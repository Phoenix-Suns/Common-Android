package com.nghiatl.common.file

object CSVFileUtil {

    /**
     * Convert CSV to Object
     * Example:
    resources.openRawResource(R.raw.contents).CSVtoObject() {
        works.add(Work(
            content = columns[1].trim(),
            duration = columns[0].trim().toInt(),
            point = columns[2].trim().toInt()
        ))
    }
     */
    private fun InputStream.CSVtoObject(
        lineColumns: ((List<String>) -> Unit)
    ) {
        this.bufferedReader(Charsets.UTF_8).useLines { lines ->
            for (line in lines) {
                val columns: List<String> = line.split(";")
                lineColumns(columns)
            }
        }
    }
}