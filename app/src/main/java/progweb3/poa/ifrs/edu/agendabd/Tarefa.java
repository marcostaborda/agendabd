package progweb3.poa.ifrs.edu.agendabd;

public class Tarefa {
    private int _id;
    private String nome;
    private String descricao;
    private String data;

    public Tarefa(){}
    public Tarefa(int id, String nome, String descricao, String data) {
        this._id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
