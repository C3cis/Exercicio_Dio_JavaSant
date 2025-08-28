
@Entity
public class Usuario {
    @Id @GeneratedValue
    private Long id;
    private String nome;
    private String tipo; 
}


@Entity
public class Livro {
    @Id @GeneratedValue
    private Long id;
    private String titulo;
    private boolean disponivel = true;
}


@Entity
public class Emprestimo {
    @Id @GeneratedValue
    private Long id;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    @ManyToOne private Usuario usuario;
    @ManyToOne private Livro livro;
}
