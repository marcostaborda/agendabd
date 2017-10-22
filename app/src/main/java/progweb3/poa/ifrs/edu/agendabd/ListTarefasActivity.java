package progweb3.poa.ifrs.edu.agendabd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class ListTarefasActivity extends AppCompatActivity {
    private ListView listTarefas;
    private Button botaoVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tarefas);
        listTarefas = (ListView)this.findViewById(R.id.listViewTarefas);
        this.getAll();
    }
     protected  void getAll(){
         TarefaRepository tarefaRepository = new TarefaRepository(this);
        List<Tarefa> tarefas = tarefaRepository.getAll();
        listTarefas.setAdapter(new LinhaConsultaAdapter(this, tarefas));
    }
}
