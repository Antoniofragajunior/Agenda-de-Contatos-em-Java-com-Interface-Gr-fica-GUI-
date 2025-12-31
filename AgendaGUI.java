package nota3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgendaGUI extends JFrame {
    private Agenda agenda;
    private JTable tabelaContatos;
    private DefaultTableModel tableModel;
    
    // Campos de entrada
    private JTextField campoCPF;
    private JTextField campoNome;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JTextField campoBuscaCPF;
    
    public AgendaGUI() {
        agenda = new Agenda();
        inicializarComponentes();
        configurarJanela();
    }
    
    private void inicializarComponentes() {
        setTitle("Agenda Eletrônica - POO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior para cadastro
        JPanel painelCadastro = new JPanel(new GridLayout(5, 2, 10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro de Contato"));
        
        // Adicionar componentes ao painel de cadastro
        painelCadastro.add(new JLabel("CPF:"));
        campoCPF = new JTextField();
        painelCadastro.add(campoCPF);
        
        painelCadastro.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelCadastro.add(campoNome);
        
        painelCadastro.add(new JLabel("E-mail:"));
        campoEmail = new JTextField();
        painelCadastro.add(campoEmail);
        
        painelCadastro.add(new JLabel("Telefone:"));
        campoTelefone = new JTextField();
        painelCadastro.add(campoTelefone);
        
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarContato();
            }
        });
        
        painelCadastro.add(new JLabel()); // Espaço vazio
        painelCadastro.add(btnCadastrar);
        
        // Painel central - busca e tabela
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        
        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBorder(BorderFactory.createTitledBorder("Buscar/Excluir por CPF"));
        
        painelBusca.add(new JLabel("CPF:"));
        campoBuscaCPF = new JTextField(15);
        painelBusca.add(campoBuscaCPF);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarContato();
            }
        });
        painelBusca.add(btnBuscar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirContato();
            }
        });
        painelBusca.add(btnExcluir);
        
        painelCentral.add(painelBusca, BorderLayout.NORTH);
        
        // Tabela de contatos
        String[] colunas = {"CPF", "Nome", "E-mail", "Telefone"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaContatos = new JTable(tableModel);
        tabelaContatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaContatos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Contatos Cadastrados"));
        
        painelCentral.add(scrollPane, BorderLayout.CENTER);
        
        // Painel inferior - botões adicionais
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton btnLimpar = new JButton("Limpar Campos");
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        
        JButton btnListarTodos = new JButton("Listar Todos");
        btnListarTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTabela();
            }
        });
        
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnListarTodos);
        
        // Adicionar todos os painéis à janela
        add(painelCadastro, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void configurarJanela() {
        setSize(700, 500);
        setLocationRelativeTo(null); // Centralizar
        setResizable(true);
    }
    
    // Métodos de ação
    private void cadastrarContato() {
        try {
            String cpf = campoCPF.getText();
            String nome = campoNome.getText();
            String email = campoEmail.getText();
            String telefone = campoTelefone.getText();
            
            Contato novoContato = new Contato(cpf, nome, email, telefone);
            
            if (agenda.cadastrar(novoContato)) {
                JOptionPane.showMessageDialog(this, 
                    "Contato cadastrado com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "CPF já cadastrado na agenda!", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro: " + e.getMessage(), 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarContato() {
        String cpfBusca = campoBuscaCPF.getText().trim();
        
        if (cpfBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Digite um CPF para buscar!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Contato contato = agenda.buscarContato(cpfBusca);
        
        if (contato != null) {
            campoCPF.setText(contato.getCpf());
            campoNome.setText(contato.getNome());
            campoEmail.setText(contato.getEmail());
            campoTelefone.setText(contato.getTelefone());
            
            // Selecionar na tabela
            selecionarNaTabela(contato.getCpf());
            
            JOptionPane.showMessageDialog(this, 
                "Contato encontrado!", 
                "Busca", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Contato não encontrado!", 
                "Busca", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void excluirContato() {
        String cpfExcluir = campoBuscaCPF.getText().trim();
        
        if (cpfExcluir.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Digite um CPF para excluir!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Deseja excluir o contato com CPF: " + cpfExcluir + "?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            if (agenda.excluirContato(cpfExcluir)) {
                JOptionPane.showMessageDialog(this, 
                    "Contato excluído com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Contato não encontrado!", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void selecionarNaTabela(String cpf) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(cpf)) {
                tabelaContatos.setRowSelectionInterval(i, i);
                break;
            }
        }
    }
    
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpar tabela
        
        for (Contato contato : agenda.listarContatos()) {
            Object[] linha = {
                contato.getCpf(),
                contato.getNome(),
                contato.getEmail(),
                contato.getTelefone()
            };
            tableModel.addRow(linha);
        }
        
        setTitle("Agenda Eletrônica - " + agenda.getQuantidadeContatos() + " contatos");
    }
    
    private void limparCampos() {
        campoCPF.setText("");
        campoNome.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        campoBuscaCPF.setText("");
        campoCPF.requestFocus();
    }
    
    // MÉTODO MAIN ÚNICO E SIMPLIFICADO
    public static void main(String[] args) {
        // Garantir que a GUI roda na thread adequada
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AgendaGUI agenda = new AgendaGUI();
                agenda.setVisible(true);
            }
        });
    }
}