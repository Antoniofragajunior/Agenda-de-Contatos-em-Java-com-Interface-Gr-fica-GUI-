package nota3;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Contato> contatos;

    public Agenda() {
        this.contatos = new ArrayList<>();
    }

    public boolean cadastrar(Contato contato) {
        // Verificar se CPF já existe
        if (buscarContato(contato.getCpf()) != null) {
            return false; // CPF já cadastrado
        }
        contatos.add(contato);
        return true;
    }

    public Contato buscarContato(String cpf) {
        for (Contato contato : contatos) {
            if (contato.getCpf().equals(cpf.trim())) {
                return contato;
            }
        }
        return null;
    }

    public boolean excluirContato(String cpf) {
        Contato contato = buscarContato(cpf);
        if (contato != null) {
            contatos.remove(contato);
            return true;
        }
        return false;
    }

    public List<Contato> listarContatos() {
        return new ArrayList<>(contatos);
    }

    public int getQuantidadeContatos() {
        return contatos.size();
    }
    
    public List<Contato> getContatos() {
        return new ArrayList<>(contatos);
    }
    
    public void setContatos(List<Contato> contatos) {
        this.contatos = new ArrayList<>(contatos);
    }
}