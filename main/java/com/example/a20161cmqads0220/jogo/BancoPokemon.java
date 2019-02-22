package com.example.a20161cmqads0220.jogo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 20161cmq.ads0220 on 03/08/2018.
 */

public class BancoPokemon extends SQLiteOpenHelper {

    private final String TABELAJOGADORES = "jogadores";
    private final String TABELACRIATURASJOGADOR = "criaturasjogador";
    private final String TABELAVINCULO = "tabelavinculo";

    private final String COLUNA_ID = "_id";
    private final String COLUNA_NOME = "nome";
    private final String COLUNA_APELIDO = "apelido";
    private final String COLUNA_SENHA = "senha";
    private final String COLUNA_EMAIL = "email";
    private final String COLUNA_ATAQUE = "ataque";
    private final String COLUNA_DEFESA = "defesa";
    private final String COLUNA_VELOCIDADE = "velocidade";
    private final String COLUNA_VIDA = "vida";
    private final String COLUNA_PONTOS = "pontos";
    private final String COLUNA_IMAGEMCRIATURA = "imagemcriatura";
    private final String COLUNA_ID_JOGADOR = "_id_jogador";
    private final String COLUNA_ID_CRIATURA = "_id_criatura";



    public BancoPokemon(Context context, String nome, int versao) {
        super(context, nome, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABELAJOGADORES + " ("
                + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement,"
                +COLUNA_NOME + " varchar(50) NOT NULL,"
                +COLUNA_APELIDO + " varchar(20) NOT NULL ,"
                +COLUNA_SENHA + " varchar(20) NOT NULL ,"
                +COLUNA_EMAIL + " varchar(50) NOT NULL"
                +");");

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABELAVINCULO + " ("
                + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement,"
                +COLUNA_ID_JOGADOR + " INTEGER NOT NULL,"
                +COLUNA_ID_CRIATURA + " INTEGER NOT NULL ,"
                +"FOREIGN KEY("+COLUNA_ID_JOGADOR+") REFERENCES "+TABELAJOGADORES+"("+COLUNA_ID+"),"
                +"FOREIGN KEY("+COLUNA_ID_CRIATURA+") REFERENCES "+TABELACRIATURASJOGADOR+"("+COLUNA_ID+")"
                +");");

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABELACRIATURASJOGADOR + " ("
                + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement,"
                +COLUNA_NOME + " varchar(50) NOT NULL,"
                +COLUNA_ATAQUE + " varchar(10) NOT NULL ,"
                +COLUNA_DEFESA + " varchar(10) NOT NULL ,"
                +COLUNA_VELOCIDADE + " varchar(10) NOT NULL,"
                +COLUNA_VIDA + " varchar(10) NOT NULL,"
                +COLUNA_PONTOS + " int NOT NULL,"
                +COLUNA_IMAGEMCRIATURA + " int NOT NULL"
                +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABELAJOGADORES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABELACRIATURASJOGADOR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABELAVINCULO);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<Jogador> lerJogadores(){
        Cursor cursor = this.getWritableDatabase().query(TABELAJOGADORES,
                new String[]{COLUNA_ID, COLUNA_NOME, COLUNA_APELIDO, COLUNA_EMAIL, COLUNA_SENHA},
                null, null, null, null, null);

        ArrayList<Jogador> jogadores = new ArrayList<>();
        for (int c =0; c<cursor.getCount(); c++){
            cursor.moveToNext();
            Jogador jogador = new Jogador();
            jogador.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
            jogador.setApelido(cursor.getString(cursor.getColumnIndexOrThrow("apelido")));
            jogador.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            jogador.setSenha(cursor.getString(cursor.getColumnIndexOrThrow("senha")));
            jogadores.add(jogador);
        }
        cursor.close();
        return jogadores;
    }

    public Jogador lerJogador(String nome, String senha){
        Cursor cursor= this.getWritableDatabase().query(TABELAJOGADORES,
                new String[]{COLUNA_ID, COLUNA_NOME, COLUNA_APELIDO, COLUNA_EMAIL, COLUNA_SENHA},
                "nome = '"+nome+"' and senha = '"+senha+"'",null,null,null,null);

        Jogador jogador = null;
        if (cursor.getCount() >= 1){
            cursor.moveToFirst();
            jogador =new Jogador();
            jogador.setId(cursor.getInt(0));
            jogador.setNome(cursor.getString(1));
            jogador.setApelido(cursor.getString(2));
            jogador.setEmail(cursor.getString(3));
            jogador.setSenha(cursor.getString(4));
        }
        cursor.close();
        return jogador;
    }

    public criatura lerCriatura(int id){
        Cursor cursor= this.getWritableDatabase().query(TABELACRIATURASJOGADOR,
                new String[]{COLUNA_ID, COLUNA_NOME, COLUNA_ATAQUE, COLUNA_DEFESA, COLUNA_VELOCIDADE, COLUNA_VIDA, COLUNA_PONTOS, COLUNA_IMAGEMCRIATURA},
                "_id = "+id,null,null,null,null);

        criatura criatura = null;
        if (cursor.getCount() >= 1){
            cursor.moveToFirst();
            criatura =new criatura();
            criatura.setId(cursor.getInt(0));
            criatura.setNomeCriatura(cursor.getString(1));
            criatura.setAtaque(cursor.getString(2));
            criatura.setDefesa(cursor.getString(3));
            criatura.setVelocidade(cursor.getString(4));
            criatura.setVida(cursor.getString(5));
            criatura.setPontos(Integer.parseInt(cursor.getString(6)));
            criatura.setImagemCriatura(Integer.parseInt(cursor.getString(7)));
        }
        cursor.close();
        return criatura;
    }

    public int buscaVinculo(int id, Jogador jogador){
        int retorno=0;
        Cursor cursor= this.getWritableDatabase().query(TABELAVINCULO,
                new String[]{COLUNA_ID, COLUNA_ID_JOGADOR, COLUNA_ID_CRIATURA},
                "_id = "+id+" and _id_jogador="+jogador.getId(),null,null,null,null);


        if (cursor.getCount() >=1){
            cursor.moveToFirst();
            retorno=cursor.getInt(2);
        }
        cursor.close();
        return retorno;
    }

    public void deletarVinculo(int id_jogador, int id_criatura){
        String where = "_id_jogador ="+id_jogador+" and _id_criatura ="+id_criatura;
        this.getWritableDatabase().delete(TABELAVINCULO, where, null);
        this.close();
    }

    public int countVinculos() {
        String countQuery = "SELECT  * FROM " + TABELAVINCULO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public int getLastID()
    {
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT MAX(_id) FROM "+TABELAVINCULO, null);
        int maxid = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
        return maxid;
    }


    public void gravarJogador(Jogador jogador){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome",jogador.getNome());
        contentValues.put("apelido",jogador.getApelido());
        contentValues.put("email",jogador.getEmail());
        contentValues.put("senha",jogador.getSenha());
        this.getWritableDatabase().insert(TABELAJOGADORES, null, contentValues);
    }

    public void gravarCriaturas(criatura criaturas){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome",criaturas.getNomeCriatura());
        contentValues.put("ataque",criaturas.getAtaque());
        contentValues.put("defesa",criaturas.getDefesa());
        contentValues.put("velocidade",criaturas.getVelocidade());
        contentValues.put("vida",criaturas.getVida());
        contentValues.put("pontos",criaturas.getPontos());
        contentValues.put("imagemcriatura",criaturas.getImagemCriatura());
        this.getWritableDatabase().insert(TABELACRIATURASJOGADOR, null, contentValues);
    }

    public void gravarVinculo(Jogador jogador, criatura criaturas){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id_jogador",jogador.getId());
        contentValues.put("_id_criatura",criaturas.getId());
        this.getWritableDatabase().insert(TABELAVINCULO, null, contentValues);
    }

    public void atualizarJogador(Jogador jogador){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome",jogador.getNome());
        contentValues.put("apelido",jogador.getApelido());
        contentValues.put("email",jogador.getEmail());
        contentValues.put("senha",jogador.getSenha());
        this.getWritableDatabase().update(TABELAJOGADORES, contentValues, "_id = "+jogador.getId(),null);
    }

    public void deletarJogador(Jogador jogador){
        String where = "_id ="+ jogador.getId();
        this.getWritableDatabase().delete(TABELAJOGADORES, where, null);
        this.close();
    }

    public void deletarCriatura(int id){
        String where = "_id ="+id;
        this.getWritableDatabase().delete(TABELACRIATURASJOGADOR, where, null);
        this.close();
    }

    public void popula(){
        criatura c1 = new criatura();
        criatura c2 = new criatura();
        criatura c3 = new criatura();
        criatura c4 = new criatura();
        criatura c5 = new criatura();
        criatura c6 = new criatura();
        c1.setNomeCriatura("Charmeleon");
        c2.setNomeCriatura("Charizard");
        c3.setNomeCriatura("Blastoise");
        c4.setNomeCriatura("Pikachu");
        c5.setNomeCriatura("Rattata");
        c6.setNomeCriatura("Butterfree");
        c1.setAtaque(Integer.toString(50));
        c2.setAtaque(Integer.toString(60));
        c3.setAtaque(Integer.toString(40));
        c4.setAtaque(Integer.toString(90));
        c5.setAtaque(Integer.toString(70));
        c6.setAtaque(Integer.toString(80));
        c1.setDefesa(Integer.toString(55));
        c2.setDefesa(Integer.toString(65));
        c3.setDefesa(Integer.toString(48));
        c4.setDefesa(Integer.toString(88));
        c5.setDefesa(Integer.toString(36));
        c6.setDefesa(Integer.toString(21));
        c1.setVelocidade(Integer.toString(70));
        c2.setVelocidade(Integer.toString(50));
        c3.setVelocidade(Integer.toString(74));
        c4.setVelocidade(Integer.toString(99));
        c5.setVelocidade(Integer.toString(87));
        c6.setVelocidade(Integer.toString(94));
        c1.setVida(Integer.toString(100));
        c2.setVida(Integer.toString(100));
        c3.setVida(Integer.toString(100));
        c4.setVida(Integer.toString(100));
        c5.setVida(Integer.toString(100));
        c6.setVida(Integer.toString(100));
        c1.setImagemCriatura(R.drawable.monstro1);
        c2.setImagemCriatura(R.drawable.monstro2);
        c3.setImagemCriatura(R.drawable.monstro3);
        c4.setImagemCriatura(R.drawable.pikachu);
        c5.setImagemCriatura(R.drawable.rattata);
        c6.setImagemCriatura(R.drawable.butterfree);
        gravarCriaturas(c1);
        gravarCriaturas(c2);
        gravarCriaturas(c3);
        gravarCriaturas(c4);
        gravarCriaturas(c5);
        gravarCriaturas(c6);
    }



}