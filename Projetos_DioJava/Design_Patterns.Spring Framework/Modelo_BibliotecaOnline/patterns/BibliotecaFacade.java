@Component
public class BibliotecaFacade {
    @Autowired private BibliotecaService service;
    private CalculoMultaStrategy strategy;

    public Emprestimo realizarEmprestimo(Long usuarioId, Long livroId) {
        return service.emprestar(usuarioId, livroId);
    }

    public Emprestimo getEmprestimo(Long id) {
        return service.getEmprestimo(id);
    }

    public void aplicarEstrategiaPorUsuario(Usuario u) {
        if ("vip".equalsIgnoreCase(u.getTipo())) setStrategy(new MultaVIP());
        else setStrategy(new MultaSimples());
    }

    public void setStrategy(CalculoMultaStrategy strategy) {
        this.strategy = strategy;
    }

    public double calcularMulta(Emprestimo e) {
        long diasAtraso = ChronoUnit.DAYS.between(
            e.getDataDevolucao() != null ? e.getDataDevolucao() : LocalDate.now(),
            LocalDate.now()
        );
        return strategy.calcularMulta(Math.max(diasAtraso, 0));
    }
}
