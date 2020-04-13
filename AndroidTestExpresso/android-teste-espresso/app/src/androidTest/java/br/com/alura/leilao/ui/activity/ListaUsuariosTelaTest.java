package br.com.alura.leilao.ui.activity;


import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.com.alura.leilao.ui.dialog.NovoUsuarioDialog.DESCRICAO_BOTAO_POSITIVO;
import static org.hamcrest.Matchers.allOf;

public class ListaUsuariosTelaTest extends BaseTesteIntegracao {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> mainActivityTestRule
            = new ActivityTestRule<>(ListaLeilaoActivity.class);

    @Before
    public void setup() {
        limparBandoDeTeste();
    }

    @Test
    public void deve_ApareceUmUsuarioNaListaDeUsuarios_QuandoCadastrarUmUsuario() {
        onView(allOf(withId(R.id.lista_leilao_menu_usuarios),
//              isDescendantOfA(withId(R.id.action_bar)) não é obrigatório,
//              Depende muito do tipo de teste que está sendo realizado e onde
//              se encontra a View!
                isDescendantOfA(withId(R.id.action_bar)),
                isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                isDisplayed()))
                .perform(click());


        onView(withId(R.id.form_usuario_nome_edit_text))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(replaceText("Elias"));

        onView(withId(android.R.id.button1))
                .check(matches(withText(DESCRICAO_BOTAO_POSITIVO)))
                .perform(click());

        onView(withId(R.id.item_usuario_id_com_nome))
                .check(matches(isDisplayed()))
                .check(matches(withText("(1) Elias")));
    }


    @Before
    public void tearDown() {
        limparBandoDeTeste();
    }
}
