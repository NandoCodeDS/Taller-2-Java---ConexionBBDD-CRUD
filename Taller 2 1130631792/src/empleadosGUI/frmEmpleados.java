package empleadosGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

import empleadosDAL.conexion;
import empleadosBL.empleadosBL;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class frmEmpleados extends JFrame {
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField txtCorreo;
	JButton btnAdicionar = new JButton("Adicionar");
	JButton btnModificar = new JButton("Modificar");
	JButton btnEliminar = new JButton("Eliminar");
	JButton btnCancelar = new JButton("Cancelar");
	
	DefaultTableModel modelo;
	
	private JTable tblEmpleados;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmEmpleados frame = new frmEmpleados();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public frmEmpleados() {
		setTitle("Administrador de Bases de Datos Empleados");
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmEmpleados.class.getResource("/recursos/transferencia-de-datos (1).png")));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 213, 512, 196);
		getContentPane().add(scrollPane);
		
		tblEmpleados = new JTable();
		tblEmpleados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1) {
					JTable receptor= (JTable)e.getSource();
					txtId.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(),0).toString());
					txtNombre.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(),1).toString());
					txtCorreo.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(),2).toString());
				}
				btnAdicionar.setEnabled(false);
				btnModificar.setEnabled(true);
				btnEliminar.setEnabled(false);
			}
		});
		
		modelo = new DefaultTableModel();
		tblEmpleados.setModel(modelo);
		modelo.addColumn("ID");
		modelo.addColumn("Nombre");
		modelo.addColumn("Correo");
		tblEmpleados.getColumnModel().getColumn(0).setPreferredWidth(25);
		tblEmpleados.getColumnModel().getColumn(1).setPreferredWidth(150);
		tblEmpleados.getColumnModel().getColumn(2).setPreferredWidth(170);
		scrollPane.setViewportView(tblEmpleados);
		
		mostrarDatos();
		//limpieza();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 456);
		getContentPane().setLayout(null);
		btnAdicionar.setIcon(new ImageIcon(frmEmpleados.class.getResource("/recursos/calidad-de-los-datos.png")));
		
		
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexion objConexion = new conexion();
				empleadosBL oEmpleados = recuperarDatosGUI();
				String strSentenciaInsert = String.format("INSERT INTO Empleados (ID, Nombre, Correo) "
						+ "VALUES (null,'%s', '%s')",oEmpleados.getNombre(),oEmpleados.getCorreo());
				objConexion.ejecutarComandoSQL(strSentenciaInsert);
				mostrarDatos();
				limpieza();
				try {
					ResultSet resultado =objConexion.consultarregistro("SELECT * FROM Empleados");
					while (resultado.next()) {
						System.out.println(resultado.getString("ID"));
						System.out.println(resultado.getString("Nombre"));
						System.out.println(resultado.getString("Correo"));
					}
				} catch (Exception e2) {
					System.out.println(e);
				}
			}
		});	
		btnAdicionar.setBounds(10, 179, 120, 23);
		getContentPane().add(btnAdicionar);
		btnModificar.setIcon(new ImageIcon(frmEmpleados.class.getResource("/recursos/enviar-datos.png")));
		
		
		
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexion objConexion = new conexion();
				empleadosBL oEmpleados = recuperarDatosGUI();
				String strSentenciaInsert = String.format("UPDATE Empleados SET Nombre='%s',"
						+ "Correo='%s' WHERE ID=%d",oEmpleados.getNombre(),oEmpleados.getCorreo(),oEmpleados.getID());
				objConexion.ejecutarComandoSQL(strSentenciaInsert);
				mostrarDatos();
				limpieza();
			}
		});
		btnModificar.setBounds(140, 179, 120, 23);
		getContentPane().add(btnModificar);
		btnEliminar.setIcon(new ImageIcon(frmEmpleados.class.getResource("/recursos/eliminar-base-de-datos.png")));
		
		
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conexion objConexion = new conexion();
				empleadosBL oEmpleados = recuperarDatosGUI();
				String strSentenciaInsert = String.format("DELETE FROM Empleados WHERE ID=%d",oEmpleados.getID());
				objConexion.ejecutarComandoSQL(strSentenciaInsert);
				mostrarDatos();
				limpieza();
			}
		});
		btnEliminar.setBounds(270, 179, 120, 23);
		getContentPane().add(btnEliminar);
		btnCancelar.setIcon(new ImageIcon(frmEmpleados.class.getResource("/recursos/prohibido.png")));
		
		
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpieza();
			}
		});
		btnCancelar.setBounds(400, 179, 120, 23);
		getContentPane().add(btnCancelar);
		
		
		JLabel ID = new JLabel("ID:");
		ID.setBounds(10, 11, 130, 14);
		getContentPane().add(ID);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(10, 29, 65, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(10, 53, 130, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(10, 78, 512, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Correo:");
		lblNewLabel_2.setBounds(10, 109, 130, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtCorreo = new JTextField();
		txtCorreo.setBounds(13, 133, 509, 20);
		getContentPane().add(txtCorreo);
		txtCorreo.setColumns(10);
	}
	
	
	public void limpieza() {
		if(txtId.getText() != null) {
			txtId.setText("");			
		}else {
			
		}
		
		txtNombre.setText("");
		txtCorreo.setText("");
		btnAdicionar.setEnabled(true);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);
	}
	
	/******************************************************/
	/*RECUPERAR LOS DATOS DE LA BBDD*/
	public empleadosBL recuperarDatosGUI() {
		empleadosBL oEmpleados = new empleadosBL();
		int ID = (txtId.getText().isEmpty())?0:Integer.parseInt(txtId.getText());
		oEmpleados.setID(ID);
		oEmpleados.setNombre(txtNombre.getText());
		oEmpleados.setCorreo(txtCorreo.getText());
		return oEmpleados;
	}
	
	/******************************************************/
	/*MOSTRAR LOS DATOS EN LA TABLA*/
	public void mostrarDatos() {
		while(modelo.getRowCount()>0) {
			modelo.removeRow(0);
		}
		conexion objConexion = new conexion();
		try {
			ResultSet resultado =objConexion.consultarregistro("SELECT * FROM Empleados");
			while (resultado.next()) {
				System.out.println(resultado.getString("ID"));
				System.out.println(resultado.getString("Nombre"));
				System.out.println(resultado.getString("Correo"));
				
				Object[] oUsuario= {resultado.getString("ID"),resultado.getString("Nombre"),resultado.getString("Correo")};
				modelo.addRow(oUsuario);
			}
		} catch (Exception e) {
			System.out.println(e);
		}			
	}
}
