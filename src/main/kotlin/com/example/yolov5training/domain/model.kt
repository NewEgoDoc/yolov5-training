package com.example.yolov5training.domain

import com.example.yolov5training.support.jpa.BaseAggregateRoot
import jakarta.persistence.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime

@Entity
@Table(name = "models")
data class Model(
    @Column(name = "batch_size", nullable = false)
    val batchSize: Int,

    @Column(name = "epoch", nullable = false)
    val epoch: Int,

    @Column(name = "train_val_rate", nullable = false)
    val trainValRate: String,

    @Column(name = "map0_5", nullable = false)
    val map0_5: Float,

    @Column(name = "map0_5_to_0_95", nullable = false)
    val map0_5To095: Float,

    @Column(name = "useyn", nullable = false)
    val useYn: Boolean,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) : BaseAggregateRoot<Model>() {
    fun train(){
        val command = listOf(
            "python",
            "-m", "torch.distributed.run",
            "--nproc_per_node", "2",
            "--master_port", "1234",
            "/home/aci/train_aci/yolov5/train.py",
            "--batch-size", "1000",
            "--epochs", "10",
            "--weights", "/home/aci/train_aci/yolov5/best.pt",
            "--data", "/home/aci/data/data.yaml",
            "--hyp", "hyp.scratch-low.yaml",
            "--device", "0,1",
            "--exist-ok"
        )

        val processBuilder = ProcessBuilder(command)
        processBuilder.redirectErrorStream(true)

        try {
            val process = processBuilder.start()

            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                println(line)
            }

            val exitCode = process.waitFor()
            println("Exit code: $exitCode")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun transferLearning(
        existOk: Boolean = true
    ) {
        val command = listOf(
            "python",
            "-m", "torch.distributed.run",
            "--nproc_per_node", "2",
            "--master_port", "1234",
            "/home/aci/train_aci/yolov5/train.py",
            "--batch-size", "1000",
            "--epochs", "10",
            "--weights", "/home/aci/train_aci/yolov5/best.pt",
            "--data", "/home/aci/data/data.yaml",
            "--hyp", "hyp.scratch-low.yaml",
            "--device", "0,1",
            "--exist-ok"
        )

        val processBuilder = ProcessBuilder(command)
        processBuilder.redirectErrorStream(true)

        try {
            val process = processBuilder.start()

            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                println(line)
            }

            val exitCode = process.waitFor()
            println("Exit code: $exitCode")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}