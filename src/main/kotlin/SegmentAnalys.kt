fun printKMax(arr: IntArray, n: Int, k: Int) {
    var j: Int
    var max: Int
    for (i in 0..n - k) {
        max = arr[i]
        j = 1
        while (j < k) {
            if (arr[i + j] > max) max = arr[i + j]
            j++
        }
        print("$max ")
    }
}

fun main(args: Array<String>) {
    val arr = intArrayOf(2, 5, 4, 6, 8)
    val k = 4
    printKMax(arr, arr.size, k)
}
