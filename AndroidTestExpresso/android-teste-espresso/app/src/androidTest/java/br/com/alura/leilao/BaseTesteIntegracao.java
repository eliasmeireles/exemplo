package br.com.alura.leilao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Leilao;

public abstract class BaseTesteIntegracao {

    private static final String ERRO_FALHA_LIMPEZA_DE_DADOS_DA_API = "Banco de dados não foi limpo";
    private static final String LEILAO_NAO_FOI_SALVO = "Leilão não foi salvo: ";
    protected FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();

    private final TesteWebClient webClient = new TesteWebClient();


    protected List<Leilao> criarETentaSalvarListaDeLeiloes(int totalDeLeiloesAhSeremCriados) throws IOException {
        List<Leilao> leiloes = new ArrayList<>();
        for (int i = 1; i <= totalDeLeiloesAhSeremCriados; i++) {
            Leilao leilao = new Leilao("Leilão " + i);
            leiloes.add(leilao);
            tentaSalvarLeilaoNaApi(leilao);
        }
        return leiloes;
    }

    protected void limpaBancoDeDadosDaApi() throws IOException {
        boolean bancoDeDadosNaoFoiLimpo = !webClient.limpaBancoDeDados();
        if (bancoDeDadosNaoFoiLimpo) {
            Assert.fail(ERRO_FALHA_LIMPEZA_DE_DADOS_DA_API);
        }
    }

    protected void tentaSalvarLeilaoNaApi(Leilao... leiloes) throws IOException {
        for (Leilao leilao : leiloes) {
            Leilao leilaoSalvo = webClient.salva(leilao);
            if (leilaoSalvo == null) {
                Assert.fail(LEILAO_NAO_FOI_SALVO + leilao.getDescricao());
            }
        }
    }

    protected void limparBandoDeTeste() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        appContext.deleteDatabase(BuildConfig.BANDO_DE_DADOS);
    }
}
