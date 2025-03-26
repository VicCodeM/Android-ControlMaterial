<?php
// Conexión a SQL Server usando PDO
$server = "SISTEMASV"; // Dirección IP del servidor SQL Server
$database = "ControlMaterial";
$username = "sa"; // Usuario de SQL Server
$password = "6433"; // Contraseña

try {
    // Cargar el controlador PDO para SQL Server
    $dsn = "sqlsrv:Server=$server;Database=$database";
    $conn = new PDO($dsn, $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Probar la conexión
    echo "Conexión exitosa a SQL Server!";
} catch (PDOException $e) {
    // Mostrar el error si falla la conexión
    die("Error de conexión: " . $e->getMessage());
}
?>