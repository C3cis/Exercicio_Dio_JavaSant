public class Main {
    public static void main(String[] args) {
        Iphone meuIphone = new Iphone("iPhone 14", "Preto");

        meuIphone.selecionarMusica("Imagine - John Lennon");
        meuIphone.tocar();
        meuIphone.pausar();

        meuIphone.ligar("11987654321");
        meuIphone.atender();
        meuIphone.iniciarCorreioVoz();

        meuIphone.exibirPagina("https://www.apple.com");
        meuIphone.adicionarNovaAba();
        meuIphone.atualizarPagina();
    }
}
