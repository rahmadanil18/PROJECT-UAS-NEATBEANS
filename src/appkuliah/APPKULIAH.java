import com.mysql.jdbc.Driver;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APPKULIAH extends JFrame implements ActionListener {

    Connection koneksi;
    JTable listDataSiswa;
    JLabel LabelNim, LabelNama, LabelSemester, LabelFakultas,LabelJurusan;
    JTextField txtNim, txtNama, txtSemester,txtJurusan, txtFakultas;
    JButton cmdSimpan, cmdCari, cmdUpdate, cmdDelete;

    public APPKULIAH() {
        setSize(700, 500);
        setLayout(null);

        LabelNim = new JLabel("NIM");
        LabelNama = new JLabel("NAMA");
        LabelSemester = new JLabel("SEMESTER");
        LabelJurusan = new JLabel("JURUSAN");
        LabelFakultas = new JLabel("FAKULTAS");

        LabelNim.setBounds(50, 50, 50, 30);
        LabelNama.setBounds(50, 80, 50, 30);
        LabelSemester.setBounds(50, 110, 100, 30);
        LabelJurusan.setBounds(50, 140, 100, 30);
        LabelFakultas.setBounds(50, 170, 100, 30);

        add(LabelNim);
        add(LabelNama);
        add(LabelSemester);
        add(LabelJurusan);
        add(LabelFakultas);

        txtNim = new JTextField(5);
        txtNama = new JTextField(25);
        txtSemester = new JTextField(15);
        txtJurusan = new JTextField(15);
        txtFakultas = new JTextField(15);

        txtNim.setBounds(150, 50, 200, 30);
        txtNama.setBounds(150, 80, 200, 30);
        txtSemester.setBounds(150, 110, 200, 30);
        txtJurusan.setBounds(150, 140, 200, 30);
        txtFakultas.setBounds(150, 170, 200, 30);

        add(txtNim);
        add(txtNama);
        add(txtSemester);
        add(txtJurusan);
        add(txtFakultas);

        cmdCari = new JButton("Cari Data");
        cmdCari.setBounds(360, 50, 100, 30);
        cmdCari.addActionListener(this);
        add(cmdCari);

        cmdSimpan = new JButton("Insert Data");
        cmdSimpan.setBounds(360, 85, 100, 30);
        cmdSimpan.addActionListener(this);
        add(cmdSimpan);

        cmdUpdate = new JButton("Update Data");
        cmdUpdate.setBounds(360, 120, 100, 30);
        cmdUpdate.addActionListener(this);
        add(cmdUpdate);

        cmdDelete = new JButton("Delete Data");
        cmdDelete.setBounds(360, 155, 100, 30);
        cmdDelete.addActionListener(this);
        add(cmdDelete);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // getAllData();
    }

    public static void main(String[] args) {
        new APPKULIAH();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DriverManager.registerDriver(new Driver());
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/inventoryPasim", "root", "");
            Statement stm = koneksi.createStatement();
            if (e.getSource() == cmdSimpan) {
                stm.executeUpdate("insert into tb_fakultas values ('" + txtNim.getText() + "','" + txtNama.getText() + "','" + txtSemester.getText() + "','"+txtJurusan.getText()+"','"+txtFakultas.getText()+"')");
                koneksi.close();
                JOptionPane.showMessageDialog(null, "Berhasil Ditambahkan");
                txtNim.setText("");
                txtNama.setText("");
                txtSemester.setText("");
                txtJurusan.setText("");
                txtFakultas.setText("");
                getAllData();
            } else if (e.getSource() == cmdCari) {
                String sqlStr = "SELECT `NIM` , `nama` , `SEMESTER`,`JURUSAN`,`FAKULTAS` FROM `tb_fakultas`" + " where NIM='" + txtNim.getText() + "'";
                ResultSet hasil = stm.executeQuery(sqlStr);
                while (hasil.next()) {
                    txtNim.setText(hasil.getString(1));
                    txtNama.setText(hasil.getString(2));
                    txtSemester.setText(hasil.getString(3));
                    txtJurusan.setText(hasil.getString(4));
                    txtFakultas.setText(hasil.getString(5));
                }
            } else if (e.getSource() == cmdUpdate) {
                stm.executeUpdate("UPDATE tb_fakultas SET nama='"+txtNama.getText()+"', SEMESTER='"+txtSemester.getText()+"', JURUSAN='"+txtJurusan.getText()+"', FAKULTAS='"+txtFakultas.getText()+"'WHERE NIM='"+txtNim.getText()+"'");
                koneksi.close();
                txtNim.setText("");
                txtNama.setText("");
                txtSemester.setText("");
                txtJurusan.setText("");
                txtFakultas.setText("");
                getAllData();
            } else if (e.getSource() == cmdDelete) {
                stm.executeUpdate("delete from barang where kode='" + txtNim.getText() + "'");
                koneksi.close();
                JOptionPane.showMessageDialog(null, "Berhasil Dihapus");
                txtNim.setText("");
                txtNama.setText("");
                txtSemester.setText("");
                txtJurusan.setText("");
                txtFakultas.setText("");
                getAllData();
            }
            koneksi.close();
        } catch (SQLException ex) { //jelaskan kalo duplikat entry
            JOptionPane.showMessageDialog(rootPane, ex);
            Logger.getLogger(APPKULIAH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getAllData() {
        String[] judul = {" NIM", "Nama", "semester","Jurusan","Fakultas"};
        try {
            DriverManager.registerDriver(new Driver());
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/inventoryPasim", "root", "");
            Statement stm = koneksi.createStatement();
            String sqlStr = "SELECT * FROM `tb_fakultas`";
            ResultSet hasil = stm.executeQuery(sqlStr);
            Object[][] isi = new Object[6][judul.length];
            int i = 0;
            while (hasil.next()) {
                isi[i][0] = hasil.getString(1);
                isi[i][1] = hasil.getString(2);
                isi[i][2] = hasil.getString(3);
                isi[i][3] = hasil.getString(4);
                isi[i][4] = hasil.getString(5);
                i = i + 1;
            }
            listDataSiswa = new JTable(isi, judul);
            JScrollPane jp = new JScrollPane(listDataSiswa);
            jp.setBounds(50, 200, 400, 300);
            add(jp);
            koneksi.close();

        } catch (Exception ex) {
            Logger.getLogger(APPKULIAH.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}