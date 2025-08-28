@RestController
@RequestMapping("/api/biblioteca")
public class BibliotecaController {

    @Autowired private BibliotecaFacade biblioteca;

    @PostMapping("/usuarios")
    public Usuario criarUsuario(@RequestBody Usuario u) {
        return biblioteca.service.salvarUsuario(u);
    }

    @PostMapping("/livros")
    public Livro criarLivro(@RequestBody Livro l) {
        return biblioteca.service.salvarLivro(l);
    }

    @GetMapping("/livros")
    public List<Livro> listarLivros() {
        return biblioteca.service.listarLivros();
    }

    @PostMapping("/emprestimo")
    public Emprestimo emprestar(@RequestParam Long usuarioId, @RequestParam Long livroId) {
        return biblioteca.realizarEmprestimo(usuarioId, livroId);
    }

    @PostMapping("/multa/{id}")
    public double calcularMulta(@PathVariable Long id) {
        Emprestimo e = biblioteca.getEmprestimo(id);
        biblioteca.aplicarEstrategiaPorUsuario(e.getUsuario());
        return biblioteca.calcularMulta(e);
    }
}
