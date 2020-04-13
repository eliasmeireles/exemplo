package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static br.com.alura.leilao.matchers.ViewMatchr.apareceLeilaoNaPosicao;

public class ListaLeilaoTelaTest extends BaseTesteIntegracao {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity =
            new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);

    @Before
    public void setup() throws IOException {
        limpaBancoDeDadosDaApi();
    }

    @Test
    public void deve_AparecerUmLeilao_QuandoCarregarUmLeilaoNaApi() throws IOException {
        String carro = "Carro";
        tentaSalvarLeilaoNaApi(new Leilao(carro));

        activity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(0, carro, 0.00)));
    }

    @Test
    public void deve_AparecerDoisLeiloes_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        String carro = "Carro";
        String moto = "Moto";
        tentaSalvarLeilaoNaApi(
                new Leilao(carro),
                new Leilao(moto));

        activity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(0, carro, 0.00)));

        onView(withId(R.id.lista_leilao_recyclerview))
                .check(matches(apareceLeilaoNaPosicao(1, moto, 0.00)));
    }

    @Test
    public void deve_AparecerUltimoLeilao_QuandoCarregarDezLeiloesDaApi() throws IOException {
        List<Leilao> leiloes = criarETentaSalvarListaDeLeiloes(10);

        activity.launchActivity(new Intent());

        int ultimaPosicaoDaListaDeLeiloes = leiloes.size() - 1;

        String descricaoDoUltimoLeilao = leiloes.get(ultimaPosicaoDaListaDeLeiloes).getDescricao();

        Matcher<View> recyclerViewListaLeiloes = withId(R.id.lista_leilao_recyclerview);

        onView(recyclerViewListaLeiloes)
                .perform(RecyclerViewActions.scrollToPosition(ultimaPosicaoDaListaDeLeiloes))
                .check(matches(apareceLeilaoNaPosicao(ultimaPosicaoDaListaDeLeiloes, descricaoDoUltimoLeilao, 0.00)));
    }

    @After
    public void tearDown() throws IOException {
        limpaBancoDeDadosDaApi();
    }
}