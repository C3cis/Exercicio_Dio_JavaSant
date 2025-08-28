@Service
public class BibliotecaService {
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private LivroRepository livroRepo;
    @Autowired private EmprestimoRepository emprestimoRepo;

    public Emprestimo emprestar(Long usuarioId, Long livroId) {
        Usuario u = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Livro l = livroRepo.findById(livroId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));

        if (!l.isDisponivel()) throw new RuntimeException("Livro já emprestado!");

        l.setDisponivel(false);

        Emprestimo e = new Emprestimo();
        e.setUsuario(u);
        e.setLivro(l);
        e.setDataEmprestimo(LocalDate.now());

        livroRepo.save(l);
        return emprestimoRepo.save(e);
    }

    public Emprestimo getEmprestimo(Long id) {
        return emprestimoRepo.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado"));
    }

    public List<Livro> listarLivros() { return livroRepo.findAll(); }
    public Livro salvarLivro(Livro l) { return livroRepo.save(l); }
    public Usuario salvarUsuario(Usuario u) { return usuarioRepo.save(u); }
}
