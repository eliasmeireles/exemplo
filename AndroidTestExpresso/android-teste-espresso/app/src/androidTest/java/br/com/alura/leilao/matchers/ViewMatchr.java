package br.com.alura.leilao.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ViewMatchr {

    public static Matcher<? super View> apareceLeilaoNaPosicao(final int position,
                                                               final String descricao,
                                                               final double maiorLanceEsperado) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            private Matcher<View> displayed = isDisplayed();            ;
            private final FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();
            private final String maiorLanceFormatadoEsperado = formatadorDeMoeda.formata(maiorLanceEsperado);

            @Override
            public void describeTo(Description description) {
                description.appendText("View com descrição ")
                        .appendValue(descricao)
                        .appendText(", maior lance ")
                        .appendValue(maiorLanceFormatadoEsperado)
                        .appendText(" na posição ")
                        .appendText(String.valueOf(position))
                        .appendText(" ")
                        .appendDescriptionOf(displayed);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {

                RecyclerView.ViewHolder viewHolderForAdapterPosition = item.findViewHolderForAdapterPosition(position);
                if (viewHolderForAdapterPosition != null) {
                    View viewHolderDevolvido = viewHolderForAdapterPosition.itemView;

                    boolean textViewTemDescricaoEsperada = verificaSeTemDescricaoEsperada(viewHolderDevolvido);

                    boolean textViewTemMaiorLanceEsperado = verificaSeTemMaiorLanceEsperado(viewHolderDevolvido);

                    return textViewTemDescricaoEsperada &&
                            textViewTemMaiorLanceEsperado &&
                            displayed.matches(viewHolderDevolvido);

                }

                throw new IndexOutOfBoundsException("View do viewHolder na " + position + " não foi encontrada!");
            }

            private boolean verificaSeTemMaiorLanceEsperado(View itemView) {
                TextView textViewValorMaiorLance = itemView.findViewById(R.id.item_leilao_maior_lance);

                return textViewValorMaiorLance.getText()
                        .toString().equals(maiorLanceFormatadoEsperado);
            }

            private boolean verificaSeTemDescricaoEsperada(View itemView) {
                TextView textViewDescricao = itemView.findViewById(R.id.item_leilao_descricao);
                return textViewDescricao.getText()
                        .toString().equals(descricao);
            }
        };
    }
}
