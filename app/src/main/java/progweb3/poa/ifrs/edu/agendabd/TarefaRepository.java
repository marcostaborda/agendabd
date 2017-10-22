package progweb3.poa.ifrs.edu.agendabd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TarefaRepository {
    private BDUtil bdUtil;

    public TarefaRepository(Context context){
        bdUtil =  new BDUtil(context);
    }

    public String insert(String nome, String descricao, String data){
        ContentValues valores = new ContentValues();
        valores.put("NOME", nome);
        valores.put("DESCRICAO", descricao);
        valores.put("DATA", data);
        long resultado = bdUtil.getConexao().insert("TAREFA", null, valores);
        if (resultado ==-1) {
            bdUtil.close();
            return "Erro ao inserir registro";
        }
        return "Registro Inserido com sucesso";
    }
    public Integer delete(int codigo){
        Integer linhasAfetadas = bdUtil.getConexao().delete("TAREFA","_id = ?", new String[]{Integer.toString(codigo)});
        bdUtil.close();
        return linhasAfetadas;
    }

    public List<Tarefa> getAll(){
        List<Tarefa> tarefas = new ArrayList<>();
        // monta a consulta
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("SELECT _ID, NOME, DESCRICAO, DATA ");
        stringBuilderQuery.append("FROM  TAREFA ");
        stringBuilderQuery.append("ORDER BY NOME");
        //consulta os registros que estão no BD
        Cursor cursor = bdUtil.getConexao().rawQuery(stringBuilderQuery.toString(), null);
        //aponta cursos para o primeiro registro
        cursor.moveToFirst();
        Tarefa tarefa = null;
        //Percorre os registros até atingir o fim da lista de registros
        while (!cursor.isAfterLast()){
            // Cria objetos do tipo tarefa
            tarefa =  new Tarefa();
            //adiciona os dados no objeto
            tarefa.set_id(cursor.getInt(cursor.getColumnIndex("_ID")));
            tarefa.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            tarefa.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
            tarefa.setData(cursor.getString(cursor.getColumnIndex("DATA")));
            //adiciona o objeto na lista
            tarefas.add(tarefa);
            //aponta para o próximo registro
            cursor.moveToNext();
        }
        bdUtil.close();
        //retorna a lista de objetos
        return tarefas;
    }
    public Tarefa getTarefa(int codigo){
        Cursor cursor =  bdUtil.getConexao().rawQuery("SELECT * FROM TAREFA WHERE _ID = "+ codigo,null);
        cursor.moveToFirst();
        Tarefa t = new Tarefa();
        t.set_id(cursor.getInt(cursor.getColumnIndex("_ID")));
        t.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
        t.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
        t.setData(cursor.getString(cursor.getColumnIndex("DATA")));
        bdUtil.close();
        return t;
    }

    public int update(Tarefa tarefa){
        ContentValues contentValues =  new ContentValues();
        contentValues.put("NOME",       tarefa.getNome());
        contentValues.put("DESCRICAO",   tarefa.getDescricao());
        contentValues.put("DATA",       tarefa.getData());
         //atualiza o objeto usando a chave
        int retorno = bdUtil.getConexao().update("TAREFA", contentValues, "_id = ?", new String[]{Integer.toString(tarefa.get_id())});
        bdUtil.close();
        return retorno;
    }
}
