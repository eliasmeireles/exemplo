package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.NovoLanceDialog;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.com.alura.leilao.matchers.ViewMatchr.deveTerOUsuarioNaListaDeUsuarios;
import static br.com.alura.leilao.ui.dialog.NovoLanceDialog.TITULO;
import static br.com.alura.leilao.ui.dialog.NovoUsuarioDialog.DESCRICAO_BOTAO_POSITIVO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

public class LanceLeilaoTelaTeste extends BaseTesteIntegracao {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> mainActivity =
            new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);

    @Rule
    public ActivityTestRule<ListaUsuarioActivity> listaUsuariosActivity =
            new ActivityTestRule<>(ListaUsuarioActivity.class, true, false);

    @Before
    public void setup() throws IOException {
        limparBandoDeTeste();
        limpaBancoDeDadosDaApi();
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));
        mainActivity.launchActivity(new Intent());
    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuandoReceberUmLance() throws IOException {
        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        verificaSeExisteUmUsarioCadastradoParaEfetuarUmLance();

        Usuario elias = new Usuario(1, "Elias");
        cadastarUmUsuario(elias);

        Espresso.pressBack();

        double valorDoLance = 200.0;

        proporLance(new ProporLance(elias, valorDoLance));

        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(withText(formatadorDeMoeda.formata(valorDoLance))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(withText(formatadorDeMoeda.formata(valorDoLance) + " - (" + elias.getId() + ") " + elias.getNome() + "\n")));
    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuandoReceberTresLances() {
        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        verificaSeExisteUmUsarioCadastradoParaEfetuarUmLance();

        Usuario elias = new Usuario(1, "Elias");
        Usuario marcela = new Usuario(2, "Marcela");
        Usuario roberta = new Usuario(3, "Roberta");

        cadastarUmUsuario(elias, marcela, roberta);

        Espresso.pressBack();

        double eliasValorDoLance = 200;
        double marcelaValorDoLance = 350;
        double robertaValorDoLance = 450;

        proporLance(new ProporLance(elias, eliasValorDoLance),
                new ProporLance(marcela, marcelaValorDoLance),
                new ProporLance(roberta, robertaValorDoLance));


        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(withText(formatadorDeMoeda.formata(robertaValorDoLance))))
                .check(matches(isDisplayed()));

        String listaDosMaioresLances = formatadorDeMoeda.formata(robertaValorDoLance) + " - (" + roberta.getId() + ") " + roberta.getNome() + "\n";
        listaDosMaioresLances = listaDosMaioresLances + formatadorDeMoeda.formata(marcelaValorDoLance) + " - (" + marcela.getId() + ") " + marcela.getNome() + "\n";
        listaDosMaioresLances = listaDosMaioresLances + formatadorDeMoeda.formata(eliasValorDoLance) + " - (" + elias.getId() + ") " + elias.getNome() + "\n";

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(withText(listaDosMaioresLances)));
    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuandoReceberLancesMuitoAlto() {
        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        verificaSeExisteUmUsarioCadastradoParaEfetuarUmLance();

        Usuario elias = new Usuario(1, "Elias");
        Usuario marcela = new Usuario(2, "Marcela");
        Usuario roberta = new Usuario(3, "Roberta");

        cadastarUmUsuario(elias, marcela, roberta);

        Espresso.pressBack();

        double eliasValorDoLance = 200000000;
        double marcelaValorDoLance = 350000000;
        double robertaValorDoLance = 450000000;

        proporLance(new ProporLance(elias, eliasValorDoLance),
                new ProporLance(marcela, marcelaValorDoLance),
                new ProporLance(roberta, robertaValorDoLance));


        onView(withId(R.id.lances_leilao_maior_lance))
                .check(matches(withText(formatadorDeMoeda.formata(robertaValorDoLance))))
                .check(matches(isDisplayed()));

        String listaDosMaioresLances = formatadorDeMoeda.formata(robertaValorDoLance) + " - (" + roberta.getId() + ") " + roberta.getNome() + "\n";
        listaDosMaioresLances = listaDosMaioresLances + formatadorDeMoeda.formata(marcelaValorDoLance) + " - (" + marcela.getId() + ") " + marcela.getNome() + "\n";
        listaDosMaioresLances = listaDosMaioresLances + formatadorDeMoeda.formata(eliasValorDoLance) + " - (" + elias.getId() + ") " + elias.getNome() + "\n";

        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(matches(withText(listaDosMaioresLances)));
    }

    private void verificaSeExisteUmUsarioCadastradoParaEfetuarUmLance() {
        onView(withId(R.id.alertTitle))
                .check(matches(isDisplayed()));

        onView(withId(android.R.id.message))
                .check(matches(isDisplayed()));
    }

    private void cadastarUmUsuario(Usuario... usuarios) {
        for (Usuario usuario : usuarios) {
            listaUsuariosActivity.launchActivity(new Intent());
            onView(withId(R.id.lista_usuario_fab_adiciona))
                    .perform(click());

            onView(withId(R.id.form_usuario_nome_edit_text))
                    .check(matches(isDisplayed()))
                    .perform(click())
                    .perform(typeText(usuario.getNome()));

            onView(allOf(withId(android.R.id.button1),
                    withText(DESCRICAO_BOTAO_POSITIVO)))
                    .perform(click());

            onView(withId(R.id.lista_usuario_recyclerview))
                    .check(matches(deveTerOUsuarioNaListaDeUsuarios((int) (usuario.getId() - 1), usuario.getNome())));
            Espresso.pressBack();
        }
    }


    private void proporLance(ProporLance... proporLances) {
        for (ProporLance proporLance : proporLances) {
            tentaEfetuarUmNovoLance(proporLance.valorDoLance);

            onData(is(proporLance.usuario))
                    .inRoot(isPlatformPopup())
                    .perform(click());

            onView(withText(NovoLanceDialog.DESCRICAO_BOTAO_POSITIVO))
                    .perform(click());

        }
    }

    private void tentaEfetuarUmNovoLance(Double valorDoLance) {
        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());

        onView(withText(TITULO))
                .check(matches(isDisplayed()));

        String valorEmString = formatadorDeMoeda.formata(valorDoLance)
                .replace("R$", "")
                .replace(",00", "")
                .replaceAll("\\s", "")
                .replaceAll("\\.", "");
        onView(withId(R.id.novo_lance_valor))
                .perform(click())
                .perform(typeText(valorEmString))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.form_lance_usuario))
                .perform(click());
    }

    private static class ProporLance {
        private Usuario usuario;
        private double valorDoLance;

        ProporLance(Usuario usuario, double valorDoLance) {
            this.usuario = usuario;
            this.valorDoLance = valorDoLance;
        }
    }

    @After
    public void tearDown() throws IOException {
        limparBandoDeTeste();
        limpaBancoDeDadosDaApi();
    }
}
