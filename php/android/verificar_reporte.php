<?php
// Conexión a SQL Server usando PDO
include'conect.php';

try {
    // Cargar el controlador PDO para SQL Server
    $dsn = "sqlsrv:Server=$server;Database=$database";
    $conn = new PDO($dsn, $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Recibir el ID del ticket desde GET
    $idTicket = $_GET['id_ticket'];

    // Sentencia SQL para verificar si existe el reporte
    $query = "SELECT COUNT(*) AS count FROM Reportes WHERE Id_ticket = ?";

    // Preparar y ejecutar la sentencia
    $stmt = $conn->prepare($query);
    $stmt->bindParam(1, $idTicket);
    $stmt->execute();

    // Obtener el resultado
    $result = $stmt->fetch(PDO::FETCH_ASSOC);

    // Verificar si el reporte existe
    if ($result['count'] > 0) {
        echo json_encode(["status" => "exists"]);
    } else {
        echo json_encode(["status" => "not_exists"]);
    }
    $conn = null;
} catch (PDOException $e) {
    die("Error de conexión: " . $e->getMessage());
}
?>