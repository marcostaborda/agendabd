package progweb3.poa.ifrs.edu.agendabd;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LinhaConsultaAdapter extends BaseAdapter {

    //Cria objeto LayoutInflater para ligar com a View activity_linha.xml
    private static LayoutInflater layoutInflater = null;

    List<Tarefa> tarefas =  new ArrayList<>();
    TarefaRepository tarefaRepository ;

    //Cria objeto do tipo que lista as tarefas
    private ListTarefasActivity listarAtividades;

    //Construtor que recebe a ativida como parametro e a lista de tarefas que vai retornar do BD
    public LinhaConsultaAdapter(ListTarefasActivity listarAtividades, List<Tarefa> tarefas ) {

        this.tarefas       =  tarefas;
        this.listarAtividades  =  listarAtividades;
        this.layoutInflater     = (LayoutInflater) this.listarAtividades.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tarefaRepository   = new TarefaRepository(listarAtividades);
    }

    //Retoirna a quantidade de objetos que sta na lista
    @Override
    public int getCount(){
        return tarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    //Método converte os valoes de um item  da lista de Tarefas para uma linha do ListView
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Cria um objeto para acessar o layout activity_linha.xml
        final View viewLinhaLista = layoutInflater.inflate(R.layout.activity_linha,null);

        //vincula os campos do arquivo de layout aos objetos cadastrados
        TextView textViewCodigo = (TextView) viewLinhaLista.findViewById(R.id.textViewCodigo);
        TextView textViewNome  = (TextView) viewLinhaLista.findViewById(R.id.textViewNome);
        TextView textViewDescricao = (TextView) viewLinhaLista.findViewById(R.id.textViewDescricao);
        TextView textViewData = (TextView) viewLinhaLista.findViewById(R.id.textViewData);
        Button buttonExcluir = (Button) viewLinhaLista.findViewById(R.id.buttonExcluir);
        Button buttonEditar = (Button) viewLinhaLista.findViewById(R.id.buttonEditar);

        textViewCodigo.setText(String.valueOf(tarefas.get(position).get_id()));
        textViewNome.setText(tarefas.get(position).getNome());
        textViewDescricao.setText(tarefas.get(position).getDescricao());
        textViewData.setText(tarefas.get(position).getData());

        //Criando evento para excluir um registro do BD
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensagem = "Registro excluído com sucesso!";
                Integer retorno = tarefaRepository.delete(tarefas.get(position).get_id());
                if(retorno == 0)
                    mensagem = "Erro ao excluir registro!";
                Toast.makeText(listarAtividades, mensagem, Toast.LENGTH_LONG).show();
                atualizaLista();
            }
        });

        //Criando evento para editar um registro do BD
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listarAtividades, EditTarefaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("_id",tarefas.get(position).get_id());
                listarAtividades.startActivity(intent);
            }
        });
        return viewLinhaLista;
    }

    //atualizando a lista após excluir registro
    public void atualizaLista(){
        this.tarefas.clear();
        this.tarefas = tarefaRepository.getAll();
        this.notifyDataSetChanged();
    }
}