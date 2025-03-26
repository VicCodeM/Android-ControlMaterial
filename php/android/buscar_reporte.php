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

    // Sentencia SQL para buscar el reporte
    $query = "SELECT * FROM Reportes WHERE Id_ticket = ?";

    // Preparar la sentencia
    $stmt = $conn->prepare($query);

    // Ejecutar la sentencia
    $stmt->execute([$idTicket]);

    // Obtener el resultado
    $result = $stmt->fetch(PDO::FETCH_ASSOC);

    // Convertir imágenes a Base64 si existen
    if ($result) {
        $result['Imagen_antes'] = base64_encode($result['Imagen_antes']);
        $result['Imagen_despues'] = base64_encode($result['Imagen_despues']);
    }

    // Devolver el resultado como JSON
    echo json_encode($result);

    // Cerrar la conexión
    $conn = null;
} catch (PDOException $e) {
    // Mostrar el error si falla la conexión
    die("Error de conexión: " . $e->getMessage());
}
?>