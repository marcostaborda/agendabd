package progweb3.poa.ifrs.edu.agendabd;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class EditTarefaActivity extends AppCompatActivity {
    private int _id=0;
    EditText editTextNome ;
    EditText editTextDesc;
    private  EditText editTextData;
    Button botaoEditar ;
    private DatePickerDialog datePickerDialogData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tarefa);
        editTextNome = (EditText) this.findViewById(R.id.editTextNomeEdit);
        editTextDesc = (EditText) this.findViewById(R.id.editTextDescricaoEdit);
        editTextData = (EditText) this.findViewById(R.id.editTextDataEdit);
        botaoEditar = (Button) this.findViewById(R.id.buttonEditar);

        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual   = calendarDataAtual.get(Calendar.YEAR);
        int mesAtual   = calendarDataAtual.get(Calendar.MONTH);
        int diaAtual   = calendarDataAtual.get(Calendar.DAY_OF_MONTH);

        datePickerDialogData = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                //formata o mês com dois digitos
                String mes = (String.valueOf((mesSelecionado + 1)).length() == 1 ? "0" + (mesSelecionado + 1 ): String.valueOf(mesSelecionado));
                editTextData.setText(diaSelecionado + "/" + mes + "/" + anoSelecionado);
            }}, anoAtual, mesAtual, diaAtual);


             editTextData.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                datePickerDialogData.show();
            }
        });

        //cria evento para o botão editar
        botaoEditar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alterar();
            }
        });

        TarefaRepository tarefaRepository = new TarefaRepository(this);

        //Pega o id do objeto que foi passado como parâmetro
        Bundle extra =  this.getIntent().getExtras();
        _id = extra.getInt("_id");

        //Pega o bojeto usando o id
        Tarefa tarefa = tarefaRepository.getTarefa(_id);

        editTextNome.setText(tarefa.getNome());
        editTextDesc.setText(tarefa.getDescricao());
        editTextData.setText(tarefa.getData());
    }
    private void alterar(){
        if(editTextNome.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Nome é obrigatório!", Toast.LENGTH_LONG).show();
            editTextNome.requestFocus();
        }
        else if(editTextDesc.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Descrição é obrigatório!", Toast.LENGTH_LONG).show();
            editTextDesc.requestFocus();
        }
        else if(editTextData.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "Data é obrigatório!", Toast.LENGTH_LONG).show();
            editTextData.requestFocus();
        }
        else {
            Tarefa tarefa = new Tarefa();
            tarefa.set_id(_id);
            tarefa.setNome(editTextNome.getText().toString().trim());
            tarefa.setDescricao(editTextDesc.getText().toString().trim());
            tarefa.setData(editTextData.getText().toString().trim());
            int linhasAfetadas = new TarefaRepository(this).update(tarefa);
            String msg = "Registro alterado com sucesso! ";
            if(linhasAfetadas == 0 ) msg = "Registro não foi alterado! ";
            //mostrando caixa de diálogo de sucesso
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.app_name);
            alertDialog.setMessage(msg);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // Forçando que o código retorne para a tela de consulta
                    Intent intent = new Intent(getApplicationContext(), ListTarefasActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alertDialog.show();
        }
    }

}
