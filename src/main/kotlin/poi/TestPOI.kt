package poi


import jdk.nashorn.internal.objects.NativeString
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


//val KaiserDeviceInfoFileLocation = "C:/canonicalNames.xlsx"

/***
 * Cointains the device information for devices on Kaiser, including Part Numbers and deviceIds (as stored in UsageServiceDB)
 */
const val KaiserDeviceInfoFileLocation = "C:/Users/tellezga/Documents/2018/Kaiser Device Information - Part Number Mapping"

/**
 * We will write the verified information to this file
 */
const val canonicalNamesFileLocation = "C:/Users/tellezga/Documents/2018/Canonical Names for Devices on Nydia's Excel"

const val xlsxExtension = ".xlsx"

val kaiserPartNumberToDeviceTypes: MutableMap<String, DeviceType> = HashMap<String, DeviceType>()

val kaiserPartNumberToDeviceTypeId: MutableMap<String, Int> = HashMap<String, Int>()
val kaiserPartNumberToDeviceType: MutableMap<String, String> = HashMap<String, String>()
val canonicalPartNumberTo: MutableMap<String, String> = HashMap<String, String>()


fun main(args: Array<String>) {
    println(message = "Hello, Kotlin!")
    //processStuff(KaiserDeviceInfoFileLocation)
    //processStuff(KaiserDeviceInfoFileLocation = "C:/canonicalNames.xlsx")
    val workbook = openXLSXFile(KaiserDeviceInfoFileLocation + xlsxExtension)
    processStuff(workbook)

}

fun openXLSXFile(fileLocation: String): XSSFWorkbook {
    val file = FileInputStream(File(fileLocation))
    val workbook = XSSFWorkbook(file)
    return workbook
}

fun processStuff(workbook: Workbook) {

    val sheet = workbook.getSheetAt(0)
    val data: MutableMap<Int, List<String>> = HashMap<Int, List<String>>()
    var i = 0
    /*
    for (row in sheet) {
        data.put(i, ArrayList<String>());
        for (cell in row) {
            when (cell.cellTypeEnum) {
                CellType.STRING -> print(cell.stringCellValue + "\t")
                CellType.NUMERIC -> print("" + cell.numericCellValue + "\t")
                CellType.BOOLEAN -> println("" + cell.booleanCellValue)
                else -> println("data: " + data.get(i))
            }
        }
        print("\n")
        i++
    }
*/
    println("SEPARATOR")
    //Get Part numbers from Jeff's Excel


    val kaiserDeviceInfoSheet = workbook.getSheetAt(0)
    val formatter = DataFormatter()

    //Load deviceTypeId, deviceType unto memory with partNumber as key
    // for (row in kaiserDeviceInfoSheet) {
    for (rowNum in 1..kaiserDeviceInfoSheet.lastRowNum) {
        var cell = kaiserDeviceInfoSheet.getRow(rowNum).getCell(4)
        //val cellRef = CellReference(j, 4)
        //print(cellRef.formatAsString() + " - ")
        val partNumber = formatter.formatCellValue(cell)

        val deviceTypeId = formatter.formatCellValue(kaiserDeviceInfoSheet.getRow(rowNum).getCell(1))
        val deviceTypeName = formatter.formatCellValue(kaiserDeviceInfoSheet.getRow(rowNum).getCell(2))

        if (!kaiserPartNumberToDeviceTypeId.containsKey(partNumber)) {
            val deviceType = DeviceType(partNumber = partNumber)

            deviceType.deviceTypeId = deviceTypeId.toInt()
            deviceType.deviceTypeName = deviceTypeName

            kaiserPartNumberToDeviceTypeId[partNumber] = deviceTypeId.toInt()
            kaiserPartNumberToDeviceType[partNumber] = deviceTypeName
            kaiserPartNumberToDeviceTypes[partNumber] = deviceType
        }
        //print("part: " + partNumber + ", typeId: " + deviceTypeId + ", deviceType: " + deviceType)
        //println()
    }

    //Write that info into the excel file of "canonical"
    //1. Iterate through each row of the Canonical file
    //2. Search using its partNumber on the already loaded map of devices from Kaiser
    //3. Write on the Canonical Excel the Type Id and correct Model /DeviceType Name
    //4. Use the deviceTypeId to Query the UsageDB D:
    //  4.1 Send all the deviceTypeIds as a single param, separated by |
    //  4.2 query and return, JSON maybe, or CSV, or | separated
    //  4.3 Unmarshall and compare/correct device types
    //Maybe lambda can tell which devicetypes were found and which ones were not
    /*
    *
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(2);
        Cell cell = row.getCell(3);
        if (cell == null)
            cell = row.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellValue("a test");

        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
            wb.write(fileOut);
        }
    * */


    val canonicalWorkbook = openXLSXFile(canonicalNamesFileLocation + xlsxExtension)
    val canonicalDeviceSheet = canonicalWorkbook.getSheetAt(0)
    var k = 1;

    for (rowNum in 1..canonicalDeviceSheet.lastRowNum) {
        val row = canonicalDeviceSheet.getRow(rowNum);
        //Get third column: Part number
        val cell = row.getCell(2)
        //val cellRef = CellReference(j, 4)
        val partNumber = formatter.formatCellValue(cell)

        //Get cell to write deviceTypeID, at column 5 (F)

        var devieTypeIdDestinationCell = row.getCell(5)
        if (devieTypeIdDestinationCell == null) {
            devieTypeIdDestinationCell = row.createCell(5)
        }
        devieTypeIdDestinationCell.setCellType(CellType.STRING);
        val deviceTypeIdForPartNumber = kaiserPartNumberToDeviceTypeId[partNumber]
        devieTypeIdDestinationCell.setCellValue("" + deviceTypeIdForPartNumber)

        //Get cell to write deviceTypeID, at column 6 (G)

        var deviceTypeDestinationCell = row.getCell(6)
        if (deviceTypeDestinationCell == null) {
            deviceTypeDestinationCell = row.createCell(6)
        }
        deviceTypeDestinationCell.setCellType(CellType.STRING);
        val deviceTypeNameForPartNumber = kaiserPartNumberToDeviceType[partNumber]
        deviceTypeDestinationCell.setCellValue("" + deviceTypeNameForPartNumber)



        println("partNumber: " + partNumber + ", deviceTypeId: " + deviceTypeIdForPartNumber + ", deviceTypeName: " + deviceTypeNameForPartNumber)

        //Also, Concat A + B, with a space and UPPER CASE, at column 8 (I)
        var manufacturerAndDeviceNameCell = row.getCell(8)
        if (manufacturerAndDeviceNameCell == null) {
            manufacturerAndDeviceNameCell = row.createCell(8)
        }
        manufacturerAndDeviceNameCell.setCellType(CellType.STRING);

        val manufacturerCell = row.getCell(0)
        manufacturerCell.setCellType(CellType.STRING);
        val deviceNameCell = row.getCell(1)
        deviceNameCell.setCellType(CellType.STRING);
        val concatenatedValue: String = NativeString.toUpperCase(manufacturerCell) + " " + NativeString.toUpperCase(deviceNameCell) + ""
        manufacturerAndDeviceNameCell.setCellValue("" + concatenatedValue + "")


        //Does it match the cross referenced. column 9 (J)
        val doNamesMatch = concatenatedValue.equals(deviceTypeNameForPartNumber, true)
        var matchesCell = row.getCell(9)
        if (matchesCell == null) {
            matchesCell = row.createCell(9)
        }
        val textMatches = if (doNamesMatch) "" else "nop"
        matchesCell.setCellType(CellType.STRING);
        matchesCell.setCellValue("" + textMatches)


    }

    try {

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS")
        val formatted = current.format(formatter)

        println("Current Date and Time is: $formatted")

        val fileOut = FileOutputStream(canonicalNamesFileLocation + "_" + formatted + xlsxExtension)
        canonicalWorkbook.write(fileOut)
    } catch (e: Exception) {
        println("Exception: " + e.message)
    }

}
