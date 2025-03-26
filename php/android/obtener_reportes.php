<?php
include'conect.php';
try {
    // Cargar el controlador PDO para SQL Server
    $dsn = "sqlsrv:Server=$server;Database=$database";
    $conn = new PDO($dsn, $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Sentencia SQL para obtener todos los reportes
    $query = "SELECT * FROM Reportes";

    // Preparar la sentencia
    $stmt = $conn->prepare($query);

    // Ejecutar la sentencia
    $stmt->execute();

    // Obtener los resultados y convertir imágenes a Base64
    $results = $stmt->fetchAll(PDO::FETCH_ASSOC);
    foreach ($results as &$row) {
        $row['Imagen_antes'] = base64_encode($row['Imagen_antes']);
        $row['Imagen_despues'] = base64_encode($row['Imagen_despues']);
    }

    // Devolver los resultados como JSON
    echo json_encode($results);

    // Cerrar la conexión
    $conn = null;
} catch (PDOException $e) {
    // Mostrar el error si falla la conexión
    die("Error de conexión: " . $e->getMessage());
}
?>