package com.example.td_bdd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;

    public static Connection conn = null;
    Connection connect;
    String ip, user, mdp, db;
    ResultSet rst=null;
    PreparedStatement stmt;

    private EditText champPseudo, idPassword, idMDPVerif, idComment;
    private RadioGroup idRadio, idRadio2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = "10.4.253.112:3306";
        user = "root";
        mdp = "";
        db = "td_android";

        spinner = (Spinner) findViewById(R.id.asso_spin);

        String query = "SELECT assoNom FROM td_android";

        try {
            connect = connexion(user, mdp, db, ip);
            stmt = connect.prepareStatement(query);
            rst = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            while (rst.next()) {
                String pays = rst.getString("pays");
                data.add(pays);
            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, data);
            spinner.setAdapter(NoCoreAdapter);
        } catch (SQLException e) {
            e.printStackTrace();

            StrictMode.setThreadPolicy(new
                    StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog() // Enregistre un message à logcat
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeath() //l'application se bloque, fonctionne à //la fin de toutes les sanctions permises
                    .build());

            //appel de la connexion
            //MysqlConnexion();

            //Déclarations
            champPseudo = (EditText) findViewById(R.id.champPseudo);
            idComment = (EditText) findViewById(R.id.idComment);
            idMDPVerif = (EditText) findViewById(R.id.idMDPVerif);
            idPassword = (EditText) findViewById(R.id.idPassword);

            idRadio = (RadioGroup) findViewById(R.id.idRadio);
            idRadio.setOnClickListener((View.OnClickListener) this);
            idRadio2 = (RadioGroup) findViewById(R.id.idRadio2);
            idRadio2.setOnClickListener((View.OnClickListener) this);

        /*btnVoir = (Button) findViewById(R.id.btnVoir);
        btnVoir.setOnClickListener(this);

        btnVoirTout = (Button) findViewById(R.id.btnVoirTout);
        btnVoirTout.setOnClickListener(this);

        btnFermer = (Button) findViewById(R.id.btnFermer);
        btnFermer.setOnClickListener(this);

        btnSuivant = (Button) findViewById(R.id.btnSuivant);
        btnSuivant.setOnClickListener(this);
        btnPrecedent = (Button) findViewById(R.id.btnPrecedent);
        btnPrecedent.setOnClickListener(this);*/
        }
    }


    /*private void MysqlConnexion() {
        String jdbcURL = "jdbc:mysql://10.4.253.112:3306/td_android";
        String user = "root";
        String passwd = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL,user,passwd);

        } catch ( ClassNotFoundException e) {
            Toast.makeText(MainActivity.this, "Driver manquant." + e.getMessage().toString(), Toast.LENGTH_LONG).show();

        } catch ( java.sql.SQLException ex ) {
            Toast.makeText(MainActivity.this, "Connexion au serveur impossible." + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            Log.d("error", "SQLException: " + ex.getMessage());
            Log.d("error","SQLState: " + ex.getSQLState());
            Log.d("error","VendorError: " + ex.getErrorCode());
        }
    } // fin de MysqlConnection*/

        private Connection connexion(String user, String pass, String db, String ip) {
            StrictMode.ThreadPolicy policy = new StrictMode.
                    ThreadPolicy.
                    Builder().
                    permitAll().
                    build();

            StrictMode.setThreadPolicy(policy);
            Connection conn = null;
            String ConnURL = null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                ConnURL = "jdbc:mysql://" + this.ip + "/" + this.db + "";
                conn = DriverManager.getConnection(ConnURL, this.user, this.mdp);
                Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_LONG).show();

            } catch (SQLException se) {
                Toast.makeText(MainActivity.this, "Echec connexion", Toast.LENGTH_LONG).show();
                Log.e("ERRO", se.getMessage());

            } catch (ClassNotFoundException e) {
                Log.e("ERRO", e.getMessage());

            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
            return conn;
        }
}

