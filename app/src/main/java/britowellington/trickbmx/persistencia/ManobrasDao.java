package britowellington.trickbmx.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import britowellington.trickbmx.entidade.Manobra;

/**
 * Created by Were on 04/04/2016.
 */
public class ManobrasDao {

        private Context contexto;
        private static final String TABELA_MANOBRA = "manobra";
        private static final String SQL_SELECT_TODOS = "SELECT + FROM manobra";
        private BDutil util;
        private SQLiteDatabase db;

        //Construtor- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public ManobrasDao(Context context){
            this.contexto =  context;
            this.util = new BDutil(context);
        }

        //Método Inserir- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public void inserir(Manobra manobra){
            //Objeto para armazenar os valores dos campos
            ContentValues values = new ContentValues();

            //Definição de valores dos campos das tabelas - - - - - - - - - - - - - - - - - - - - - - -
            values.put("nome",manobra.getNome());
            values.put("descricao", manobra.getDescricao());
            values.put("dica", manobra.getDica());
            // values.put("pathFoto",pessoa.getPathFoto());

            BDutil bdUtil = new BDutil(contexto);
            bdUtil.getWritableDatabase().insert(TABELA_MANOBRA,null,values);
            bdUtil.close();
        }

    // Método Listar................................................................................
    public List<Manobra> listar() throws Exception{

        // Definição da lista de Manobras
        List<Manobra> lista = new ArrayList<Manobra>();

        // Definição da Instrução SQL
        String sql = "Select [_id], nome, descricao from manobra order by nome";

        // Objeto que recebe os registros do banco de dados
        BDutil bdUtil = new BDutil(contexto);
        Cursor cursor = bdUtil.getReadableDatabase().rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {
                Manobra manobra = new Manobra();
                manobra.setId(cursor.getInt(0));
                manobra.setNome(cursor.getString(1));
                manobra.setDescricao(cursor.getString(2));
                lista.add(manobra);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            // fecha a conexão com o banco
            cursor.close();
            bdUtil.close();
        }
        return lista;
    }

    //Método deletar
    public void deletar(int id_manobra){
        try {
            db = util.getReadableDatabase();
            String where = " [_id] = " + String.valueOf(id_manobra);
            db.delete(TABELA_MANOBRA, where, null);
            Toast.makeText(this.contexto, "Feito!", Toast.LENGTH_LONG).show();
            db.close();
        }catch (Exception e){
            Log.e("Qtd",e.getMessage());
        }
    }

    public void alteraRegistro(Manobra manobra){
        ContentValues values;
        values = new ContentValues();
        db = util.getWritableDatabase();
        String where = " [_id] = " + String.valueOf(manobra.getId());

            values.put("nome", manobra.getNome());
            values.put("descricao", manobra.getDescricao());
            values.put("dica", manobra.getDica());
            db.update(TABELA_MANOBRA, values, where, null);
            db.close();


    }


}
