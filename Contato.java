package nota3;

public class Contato {
    private String cpf;
    private String nome;
    private String email;
    private String telefone;

    public Contato(String cpf, String nome, String email, String telefone) {
        setCpf(cpf);
        setNome(nome);
        setEmail(email);
        setTelefone(telefone);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio!");
        }
        this.cpf = cpf.trim();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio!");
        }
        this.nome = nome.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail não pode ser vazio!");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido! Deve conter @");
        }
        this.email = email.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio!");
        }
        this.telefone = telefone.trim();
    }

    @Override
    public String toString() {
        return String.format("CPF: %s, Nome: %s, Email: %s, Telefone: %s", 
                            cpf, nome, email, telefone);
    }
}
