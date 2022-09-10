package empleadosDAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*La clase*/
public class conexion {
	String strConexionBBDD="jdbc:sqlite:D:/Desarrollo/Proyectos/SQLite/BBDD_Taller_1PM/interJavaBBDD.s3db";
	Connection conect=null;
	
	/*el constructor*/
	public conexion() {
		try {
			Class.forName("org.sqlite.JDBC");
			conect=DriverManager.getConnection(strConexionBBDD);
			
			System.out.println("Conexión establecida");
		} catch (Exception e) {
			System.out.println("Error de conexión" + e);
		}
	}
	
	public int ejecutarComandoSQL(String strSenrtenciaSQL) {
		try {
			PreparedStatement pstm = conect.prepareStatement(strSenrtenciaSQL);
			pstm.execute();
			return 1;
		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
	}
	
	public ResultSet consultarregistro(String strSenrtenciaSQL) {
		try {
			PreparedStatement pstm = conect.prepareStatement(strSenrtenciaSQL);
			ResultSet respuesta=pstm.executeQuery();
			return respuesta;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
