<?php
// Conexión a SQL Server usando PDO
include'conect.php';

try {
    // Cargar el controlador PDO para SQL Server
    $dsn = "sqlsrv:Server=$server;Database=$database";
    $conn = new PDO($dsn, $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Sentencia SQL para obtener todos los tipos de suelo
    $query = "SELECT Tipo_Suelo FROM Tipo_suelo";

    // Ejecutar la consulta
    $stmt = $conn->prepare($query);
    $stmt->execute();

    // Obtener los resultados
    $tiposSuelo = $stmt->fetchAll(PDO::FETCH_COLUMN);

    // Devolver los resultados como JSON
    echo json_encode($tiposSuelo);
    $conn = null;
} catch (PDOException $e) {
    die("Error de conexión: " . $e->getMessage());
}
?>