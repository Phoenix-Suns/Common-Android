package com.nghiatl.common

import java.text.SimpleDateFormat
import java.util.*

object MathUtil {
    /**
     * Trả về Num2 tương đương Num1
     * Dựa trên khoảng Min1~Max1 = Min2~Max2, từ Index1 => Index2
     */
    fun calEqualNum(min1: Float, max1: Float, min2: Float, max2: Float, num1: Float) : Float {
        val numRatio1 = num1 - min1
        val ratio1 = max1 - min1
        val ratio2 = max2 - min2

        return (numRatio1 * ratio2 / ratio1) + min2
    }

    /**
     * Trả về giá trị Mới, dự trên Limit, (start: index-limit, index, end: limit+index)
     * Using: var (start, end, newPosition) = getStartEnd(0, list.size, selectedPosition, 5)
     */
    private fun getLimitStartEnd(min: Int, max: Int, index: Int, limit: Int): Triple<Int, Int, Int> {
        var start = min
        var newPos = index

        if (index > limit) {
            start = index - limit
            newPos = limit
        }

        var end = if (newPos+limit > max) max else newPos+limit

        return Triple(start, end, newPos)
    }

    /**
     * Tạo mã kiểu String, mã Hex theo thời gian thực
     * (kiểu: yyyyMMddHHmmssSSS(3 số) ngẫu nhiên) => Hex */
    fun getCodeGenerationByTime(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault())
        val longId = java.lang.Long.valueOf(dateFormat.format(Date()))
        val strId = java.lang.Long.toHexString(longId)

        val rand = Random().nextInt(999).toString() + ""

        return strId + rand
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * `Integer.MAX_VALUE - 1`.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random.nextInt
     */
    fun randInt(min: Int, max: Int): Int {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        val rand = Random()
        //Random rand = new Random(seed);  // seed tạo ra random, cùng seed => random như nhau

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        val range = max - min + 1

        return rand.nextInt(range) + min
    }
}