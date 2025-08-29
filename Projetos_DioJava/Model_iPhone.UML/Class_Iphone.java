public class Iphone implements ReprodutorMusical, AparelhoTelefonico, NavegadorInternet {
    private String modelo;
    private String cor;
    private String musicaAtual; // guarda a música selecionada

    public Iphone(String modelo, String cor) {
        this.modelo = modelo;
        this.cor = cor;
    }

    // Métodos ReprodutorMusical
    public void tocar() {
        if (musicaAtual != null) {
            System.out.println("🎵 Tocando a música: " + musicaAtual);
        } else {
            System.out.println("Nenhuma música selecionada.");
        }
    }

    public void pausar() {
        if (musicaAtual != null) {
            System.out.println("⏸ Música pausada: " + musicaAtual);
        } else {
            System.out.println("Não há música em reprodução.");
        }
    }

    public void selecionarMusica(String musica) {
        this.musicaAtual = musica;
        System.out.println("✅ Música selecionada: " + musica);
    }

    public void ligar(String numero) {
        System.out.println("📞 Ligando para " + numero + "...");
    }

    public void atender() {
        System.out.println("📲 Atendendo chamada...");
    }

    public void iniciarCorreioVoz() {
        System.out.println("📩 Iniciando correio de voz...");
    }

    public void exibirPagina(String url) {
        System.out.println("🌐 Exibindo página: " + url);
    }

    public void adicionarNovaAba() {
        System.out.println("➕ Nova aba adicionada.");
    }

    public void atualizarPagina() {
        System.out.println("🔄 Página atualizada.");
    }
}
