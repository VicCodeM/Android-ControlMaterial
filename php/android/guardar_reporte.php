<?php
// Conexión a SQL Server usando PDO
include'conect.php';

try {
    // Cargar el controlador PDO para SQL Server
    $dsn = "sqlsrv:Server=$server;Database=$database";
    $conn = new PDO($dsn, $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Recibir datos desde POST
    $ticket = $_POST['ticket'];
    $departamento = $_POST['departamento'];
    $fechaAsignacion = $_POST['fecha_asignacion'];
    $fechaReparacion = $_POST['fecha_reparacion'];
    $colonia = $_POST['colonia'];
    $tipoSuelo = $_POST['tipo_suelo'];
    $direccion = $_POST['direccion'];
    $reportante = $_POST['reportante'];
    $telefono = $_POST['telefono'];
    $reparador = $_POST['reparador'];
    $material = $_POST['material'];
    $imagenAntes = base64_decode($_POST['imagen_antes']);
    $imagenDespues = base64_decode($_POST['imagen_despues']);
    $idUsuario = $_POST['id_usuario'];

    // Sentencia SQL para insertar el reporte
    $query = "
        INSERT INTO Reportes 
        (Id_ticket, Departamento, Fecha_asignacion, Fecha_reparacion, 
         Colonia, Tipo_suelo, Direccion, Reportante, Telefono_reportante, 
         Reparador, Material, Imagen_antes, Imagen_despues, id_usuario) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    ";

    // Preparar la sentencia
    $stmt = $conn->prepare($query);

    // Vincular parámetros
    $stmt->bindParam(1, $ticket);
    $stmt->bindParam(2, $departamento);
    $stmt->bindParam(3, $fechaAsignacion);
    $stmt->bindParam(4, $fechaReparacion);
    $stmt->bindParam(5, $colonia);
    $stmt->bindParam(6, $tipoSuelo);
    $stmt->bindParam(7, $direccion);
    $stmt->bindParam(8, $reportante);
    $stmt->bindParam(9, $telefono);
    $stmt->bindParam(10, $reparador);
    $stmt->bindParam(11, $material);
    $stmt->bindParam(12, $imagenAntes, PDO::PARAM_LOB);
    $stmt->bindParam(13, $imagenDespues, PDO::PARAM_LOB);
    $stmt->bindParam(14, $idUsuario);

    // Ejecutar la sentencia
    if ($stmt->execute()) {
        echo json_encode(["status" => "success"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Error al guardar el reporte"]);
    }

    // Cerrar la conexión
    $conn = null;
} catch (PDOException $e) {
    // Mostrar el error si falla la conexión
    die("Error de conexión: " . $e->getMessage());
}
?>