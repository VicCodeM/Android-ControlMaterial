<?php
// Conexión a SQL Server usando PDO
include'conect.php';
try {
    // Cargar el controlador PDO para SQL Server
    $dsn = "sqlsrv:Server=$server;Database=$database";
    $conn = new PDO($dsn, $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Recibir el ID del ticket desde POST
    $idTicket = $_POST['id_ticket'];

    // Sentencia SQL para eliminar el reporte
    $query = "DELETE FROM Reportes WHERE Id_ticket = ?";

    // Preparar y ejecutar la sentencia
    $stmt = $conn->prepare($query);
    $stmt->bindParam(1, $idTicket);
    $stmt->execute();

    // Verificar si se eliminó algún registro
    if ($stmt->rowCount() > 0) {
        echo json_encode(["status" => "success"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Reporte no encontrado"]);
    }

    // Cerrar la conexión
    $conn = null;
} catch (PDOException $e) {
    // Mostrar el error si falla la conexión
    die("Error de conexión: " . $e->getMessage());
}
?>