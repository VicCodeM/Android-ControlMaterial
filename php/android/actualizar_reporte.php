<?php
// Conexión a SQL Server usando PDO
include'conect.php';

try {
    // Cargar el controlador PDO para SQL Server
    $dsn = "sqlsrv:Server=$server;Database=$database";
    $conn = new PDO($dsn, $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Recibir datos desde POST
    $idTicket = $_POST['id_ticket'];
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

    // Sentencia SQL para actualizar el reporte
    $query = "
        UPDATE Reportes
        SET Departamento = ?, Fecha_asignacion = ?, Fecha_reparacion = ?, Colonia = ?, Tipo_suelo = ?, 
            Direccion = ?, Reportante = ?, Telefono_reportante = ?, Reparador = ?, Material = ?, 
            Imagen_antes = ?, Imagen_despues = ?
        WHERE Id_ticket = ?
    ";

    // Preparar la sentencia
    $stmt = $conn->prepare($query);

    // Vincular parámetros
    $stmt->bindParam(1, $departamento);
    $stmt->bindParam(2, $fechaAsignacion);
    $stmt->bindParam(3, $fechaReparacion);
    $stmt->bindParam(4, $colonia);
    $stmt->bindParam(5, $tipoSuelo);
    $stmt->bindParam(6, $direccion);
    $stmt->bindParam(7, $reportante);
    $stmt->bindParam(8, $telefono);
    $stmt->bindParam(9, $reparador);
    $stmt->bindParam(10, $material);
    $stmt->bindParam(11, $imagenAntes, PDO::PARAM_LOB);
    $stmt->bindParam(12, $imagenDespues, PDO::PARAM_LOB);
    $stmt->bindParam(13, $idTicket);

    // Ejecutar la sentencia
    if ($stmt->execute()) {
        echo json_encode(["status" => "success"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Error al actualizar el reporte"]);
    }
    $conn = null;
} catch (PDOException $e) {
    die("Error de conexión: " . $e->getMessage());
}
?>