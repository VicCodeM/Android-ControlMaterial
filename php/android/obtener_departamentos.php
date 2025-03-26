<?php
// Conexión a SQL Server usando PDO
include'conect.php';

try {
    // Cargar el controlador PDO para SQL Server
    $dsn = "sqlsrv:Server=$server;Database=$database";
    $conn = new PDO($dsn, $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Sentencia SQL para obtener todos los departamentos
    $query = "SELECT Nombre_Departamento FROM Departamentos";

    // Ejecutar la consulta
    $stmt = $conn->prepare($query);
    $stmt->execute();

    // Obtener los resultados
    $departamentos = $stmt->fetchAll(PDO::FETCH_COLUMN);

    // Devolver los resultados como JSON
    echo json_encode($departamentos);
    $conn = null;
} catch (PDOException $e) {
    die("Error de conexión: " . $e->getMessage());
}
?>