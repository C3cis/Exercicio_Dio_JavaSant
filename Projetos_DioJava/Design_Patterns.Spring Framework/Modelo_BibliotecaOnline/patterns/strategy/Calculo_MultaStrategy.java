public interface CalculoMultaStrategy {
    double calcularMulta(long diasAtraso);
}

public class MultaSimples implements CalculoMultaStrategy {
    public double calcularMulta(long diasAtraso) {
        return diasAtraso * 2.0;
    }
}

public class MultaProgressiva implements CalculoMultaStrategy {
    public double calcularMulta(long diasAtraso) {
        return diasAtraso * diasAtraso * 0.5;
    }
}

public class MultaVIP implements CalculoMultaStrategy {
    public double calcularMulta(long diasAtraso) {
        return diasAtraso * 1.0;
    }
}
