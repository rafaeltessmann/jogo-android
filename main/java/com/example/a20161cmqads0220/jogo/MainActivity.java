package com.example.a20161cmqads0220.jogo;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button botaoEntrar;
    private Button botaoVoltar;
    private Button botaoAvancar;
    private Button botaosair;
    private Button botaoCriaturaVoltar;
    private Button botaoCadastrar;
    private Button botaoCadastrarFinal;
    private Button botaoFinalizaCadastro;
    private Button botaoExcluir;
    private Button botaoVerCriaturas;
    private Button botaoMundo;
    private TextView nome;
    private TextView senha;
    private TextView apelido;
    private TextView email;
    private TextView visaogeralNome;
    private TextView visaogeralApelido;
    private TextView cadastroParte2Nome;
    private TextView cadastroParte2Apelido;
    Jogador jogador = new Jogador();
    private ArrayList<criatura> startCriatura;
    private TextView cadastroCriatura1;
    private TextView cadastroCriatura2;
    private TextView cadastroCriatura3;
    private TextView nomeCriatura;
    private TextView ataqueCriatura;
    private TextView defesaCriatura;
    private TextView velocidadeCriatura;
    private TextView vidaCriatura;
    private ImageView imagem;
    private int indiceExcluir;
    private BancoPokemon banco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences login = getSharedPreferences("arquivo", Context.MODE_PRIVATE);
        Boolean iniciaLogado = login.getBoolean("logado", false);

        banco = new BancoPokemon(this,"banco", 17 );

        //=====DEIXAR DESCOMENTADO SOMENTE A PRIMEIRA VEZ QUE ABRIR O APP=====
        //banco.popula();
        //iniciaLogado=false;
        //====================================================================

        startCriatura = new ArrayList(); // INICIALIZA

        if (iniciaLogado == true) {

            Toast.makeText(getApplicationContext(), "True", Toast.LENGTH_SHORT).show();
            String usuario = login.getString("usuario", "sem usuario");
            String password = login.getString("senha", "sem senha");
            String apelido = login.getString("apelido", "sem apelido");
            String email = login.getString("email", "email");
            jogador=banco.lerJogador(usuario, password);
            //jogador.setCriaturas(ler(getFileStreamPath("arquivoCriaturas")));
            telaPerfilJogador();
        }

        if (iniciaLogado == false) {
            Toast.makeText(getApplicationContext(), "False", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.index);
            botaoEntrar = findViewById(R.id.botaoEntrarId);
            botaoEntrar.setOnClickListener(chamadaEntrar);
            botaoCadastrar = findViewById(R.id.botaoCadastrarId);
            botaoCadastrar.setOnClickListener(chamadaCadastro);
            jogador.setNome("usuario");
            jogador.setSenha("123");
            jogador.setApelido("teste");
            jogador.setEmail("email@teste.com");
        }

    }

    public View.OnClickListener chamadaEntrar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setContentView(R.layout.login);
            Log.i("avançar: ", "avançou para login");
            botaoVoltar = findViewById(R.id.loginVoltarId);
            botaoAvancar = findViewById(R.id.botaoAvancarId);
            botaoVoltar.setOnClickListener(chamadaVoltarInicio);
            botaoAvancar.setOnClickListener(chamadaAvancar);
        }
    };

    public View.OnClickListener chamadaVoltarInicio = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setContentView(R.layout.index);
            Log.i("voltar", "voltou para tela inicial");
            botaoEntrar = findViewById(R.id.botaoEntrarId);
            botaoEntrar.setOnClickListener(chamadaEntrar);
            botaoCadastrar = findViewById(R.id.botaoCadastrarId);
            botaoCadastrar.setOnClickListener(chamadaCadastro);

            SharedPreferences login = getSharedPreferences("arquivo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = login.edit();
            editor.putBoolean("logado", false);
            editor.commit();
            startCriatura = new ArrayList();
        }
    };

    public View.OnClickListener chamadaAvancar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nome = findViewById(R.id.apelidoId);
            senha = findViewById(R.id.nomeId);

            if (banco.lerJogador(nome.getText().toString(),senha.getText().toString())!=null){
                jogador=banco.lerJogador(nome.getText().toString(),senha.getText().toString());
                SharedPreferences login = getSharedPreferences("arquivo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = login.edit();
                editor.putBoolean("logado", true);
                editor.putString("usuario", nome.getText().toString());
                editor.putString("senha", senha.getText().toString());
                editor.commit();
                telaPerfilJogador();
            }else {
                Toast.makeText(getApplicationContext(), "Erro, tente novamente!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public View.OnClickListener chamadaCriaturaVoltar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startCriatura= new ArrayList();
            telaPerfilJogador();
        }
    };

    public View.OnClickListener chamadaCadastro = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setContentView(R.layout.cadastrojogador);
            Log.i("avança: ", "foi para cadastrojogador");
            botaoCadastrarFinal = findViewById(R.id.botaoCadastrarFinalId);
            botaoCadastrarFinal.setOnClickListener(chamadaCadastroFinal);
            nome = findViewById(R.id.nomeId);

            if (banco.lerJogador("rafael","123")!=null){
                jogador=banco.lerJogador("rafael","123");
                nome.setText(jogador.getNome());
            }

        }
    };

    public View.OnClickListener chamadaCadastroFinal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nome = findViewById(R.id.nomeId);
            senha = findViewById(R.id.senhaId);
            apelido = findViewById(R.id.apelidoId);
            email = findViewById(R.id.emailId);
            jogador.setNome(nome.getText().toString());
            jogador.setSenha(senha.getText().toString());
            jogador.setApelido(apelido.getText().toString());
            jogador.setEmail(email.getText().toString());

            SharedPreferences login = getSharedPreferences("arquivo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = login.edit();
            editor.putString("usuario", nome.getText().toString());
            editor.putString("senha", senha.getText().toString());
           // editor.putString("apelido", apelido.getText().toString());
           // editor.putString("email", email.getText().toString());
            editor.putBoolean("logado", true);
            editor.commit();
            banco.gravarJogador(jogador);

            jogador=banco.lerJogador(nome.getText().toString(),senha.getText().toString());

            //SIMULAÇÃO DO JOGADOR ESCOLHENDO SUAS CRIATURAS
            //GRAVA NA TABELA VINCULO JOGADOR E CRIATURA

            for (int y=1; y<=6; y++){
                banco.gravarVinculo(jogador, banco.lerCriatura(y));
            }

            setContentView(R.layout.cadastrojogador2);
            Log.i("avança: ", "foi para parte 2 do cadastrojogador: " + jogador.getNome());
            botaoFinalizaCadastro = findViewById(R.id.botaoFinalizaCadastroId);
            botaoFinalizaCadastro.setOnClickListener(chamadaFimCadastro);
            cadastroParte2Nome = findViewById(R.id.cadastroParte2NomeId);
            cadastroParte2Apelido = findViewById(R.id.cadastroParte2ApelidoId);
            cadastroParte2Nome.setText(jogador.getNome().toString());
            cadastroParte2Apelido.setText(jogador.getApelido().toString());
            cadastroCriatura1 = findViewById(R.id.cadastroCriatura1Id);
            cadastroCriatura2 = findViewById(R.id.cadastroCriatura2Id);
            cadastroCriatura3 = findViewById(R.id.cadastroCriatura3Id);
        }
    };

    public View.OnClickListener chamadaFimCadastro = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            telaPerfilJogador();
        }
    };

    public void telaPerfilJogador(){
        setContentView(R.layout.perfiljogador);
        botaosair = findViewById(R.id.sairToEntrarId);
        botaosair.setOnClickListener(chamadaVoltarInicio);
        botaoVerCriaturas = findViewById(R.id.verCriaturasId);
        botaoVerCriaturas.setOnClickListener(chamadaVerCriaturas);
        botaoMundo= findViewById(R.id.botaoMundoId);
        botaoMundo.setOnClickListener(chamadaMundo);
        visaogeralNome = findViewById(R.id.visaogeralNomeId);
        visaogeralApelido = findViewById(R.id.visaogeralApelidoId);
        visaogeralNome.setText(jogador.getNome().toString());
        visaogeralApelido.setText(jogador.getApelido().toString());


        Log.i("Total: ","vinculos "+banco.countVinculos());
        Log.i("Max","maior id: "+banco.getLastID());

        int totalVinculos=banco.getLastID();
        for (int b=0; b<=totalVinculos; b++){
            if (banco.buscaVinculo(b, jogador)!=0){
                startCriatura.add(banco.lerCriatura(banco.buscaVinculo(b, jogador)));
            }
        }
        jogador.setCriaturas(startCriatura);

        ListView listaDinamica = (ListView) findViewById(R.id.listaDinamicaId);
        CriaturaAdapter adaptador;
        adaptador = new CriaturaAdapter(this.getApplicationContext(), jogador.getCriaturas());
        listaDinamica.setAdapter(adaptador);
        listaDinamica.setOnItemClickListener(cliqueItem);
    }

    public View.OnClickListener chamadaExcluir = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //banco.deletarCriatura(jogador.getCriaturas().get(indiceExcluir).getId());
            banco.deletarVinculo(jogador.getId(), jogador.getCriaturas().get(indiceExcluir).getId());
            Log.i("excluir","indice: "+indiceExcluir);
            startCriatura= new ArrayList();
            telaPerfilJogador();
        }
    };

    public View.OnClickListener chamadaVerCriaturas = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setContentView(R.layout.telavercriaturas);
            ListView lista = (ListView) findViewById(R.id.listaCriaturasId);
            CriaturaAdapter adaptador;
            adaptador = new CriaturaAdapter(view.getContext(), jogador.getCriaturas());
            lista.setAdapter(adaptador);
            lista.setOnItemClickListener(cliqueItem);
        }
    };

    private AdapterView.OnItemClickListener cliqueItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            telaVisualizaCriatura(i);
            /*setContentView(R.layout.telaitemlistacriatura);
            itemListaTextoCriatura = findViewById(R.id.itemListaTextoCriaturaId);
            itemListaImagemCriatura = findViewById(R.id.itemListaImagemCriaturaId);
            itemListaTextoCriatura.setText(jogador.getCriaturas().get(i).getNomeCriatura());
            itemListaImagemCriatura.setImageResource(jogador.getCriaturas().get(i).getImagemCriatura());
            */
        }
    };

    public void telaVisualizaCriatura(int indice){
        setContentView(R.layout.visualizacriatura);
        Log.i("ver: ", "avançou para tela da visualizacriatura");
        botaoCriaturaVoltar = findViewById(R.id.botaoCriaturaVoltarId);
        botaoCriaturaVoltar.setOnClickListener(chamadaCriaturaVoltar);
        botaoExcluir = findViewById(R.id.botaoExcluirId);
        botaoExcluir.setOnClickListener(chamadaExcluir);
        nomeCriatura = findViewById(R.id.nomeCriaturaId);
        ataqueCriatura = findViewById(R.id.ataqueCriaturaId);
        defesaCriatura = findViewById(R.id.defesaCriaturaId);
        imagem = findViewById(R.id.imagemId);
        velocidadeCriatura = findViewById(R.id.velocidadeCriaturaId);
        vidaCriatura = findViewById(R.id.vidaCriaturaId);

        nomeCriatura.setText(jogador.getCriaturas().get(indice).getNomeCriatura());
        ataqueCriatura.setText(jogador.getCriaturas().get(indice).getAtaque());
        defesaCriatura.setText(jogador.getCriaturas().get(indice).getDefesa());
        velocidadeCriatura.setText(jogador.getCriaturas().get(indice).getVelocidade());
        vidaCriatura.setText(jogador.getCriaturas().get(indice).getVida());
        imagem.setImageResource(jogador.getCriaturas().get(indice).getImagemCriatura());
        indiceExcluir = indice;
    }

    public void gravar(File arquivo, ArrayList<criatura> lista) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        int i;
        try {
            fos = new FileOutputStream(arquivo);
            oos = new ObjectOutputStream(fos);

            for (i = 0; i < lista.size(); i++) {
                oos.writeObject(lista.get(i));
            }

            oos.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<criatura> ler(File arquivo) {
        ArrayList<criatura> criaturas = new ArrayList();
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(arquivo);
            ois = new ObjectInputStream(fis);
            while (true) {
                criaturas.add((criatura) ois.readObject());
            }

        } catch (EOFException e) {

        } catch (Exception e) {

        } finally {
            try {
                fis.close();
                ois.close();
            } catch (Exception e) {

            }
        }
        return criaturas;
    }

    private void solicitarPermissao(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
            configurarGPS();
        }
    }

    private void configurarGPS(){
        try{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener);
        }
        catch (SecurityException ex){
            Toast.makeText(this, "Não foi possível configurar o GPS!", Toast.LENGTH_LONG).show();
        }
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longetude = location.getLongitude();

            ((TextView)findViewById(R.id.id_Long)).setText(Double.toString(longetude));
            ((TextView)findViewById(R.id.id_Lat)).setText(Double.toString(latitude));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public View.OnClickListener chamadaMundo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            solicitarPermissao();
            setContentView(R.layout.mapa);
        }
    };

    // FIM MainActivity

}