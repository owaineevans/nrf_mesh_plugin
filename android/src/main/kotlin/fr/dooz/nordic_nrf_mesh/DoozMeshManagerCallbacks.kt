package fr.dooz.nordic_nrf_mesh

import android.util.Log
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import no.nordicsemi.android.mesh.MeshManagerApi
import no.nordicsemi.android.mesh.MeshManagerCallbacks
import no.nordicsemi.android.mesh.MeshNetwork
import no.nordicsemi.android.mesh.provisionerstates.UnprovisionedMeshNode

class DoozMeshManagerCallbacks(private val binaryMessenger: BinaryMessenger, var eventSink : EventChannel.EventSink?) : MeshManagerCallbacks {
    private var doozMeshNetwork: DoozMeshNetwork? = null

    override fun onNetworkImported(meshNetwork: MeshNetwork?) {
        Log.d(this.javaClass.name, "onNetworkImported")
        if (meshNetwork == null) {
            return
        }
        if (doozMeshNetwork == null || doozMeshNetwork?.meshNetwork?.id != meshNetwork.id) {
            doozMeshNetwork = DoozMeshNetwork(binaryMessenger, meshNetwork)
        } else {
            doozMeshNetwork?.meshNetwork = meshNetwork
        }
        eventSink?.success(mapOf(
                "eventName" to "onNetworkImported",
                "id" to meshNetwork.id,
                "meshName" to meshNetwork.meshName,
                "isLastSelected" to meshNetwork.isLastSelected
        ))
    }

    override fun onNetworkLoadFailed(error: String?) {
        Log.d(this.javaClass.name, "onNetworkLoadFailed")
        eventSink?.success(mapOf(
                "eventName" to "onNetworkLoadFailed",
                "error" to error
        ))
    }

    override fun onMeshPduCreated(pdu: ByteArray?) {
        Log.d(this.javaClass.name, "onMeshPduCreated")
    }

    override fun getMtu(): Int {
        Log.d(this.javaClass.name, "getMtu")
        return -1
    }

    override fun onNetworkImportFailed(error: String?) {
        Log.d(this.javaClass.name, "onNetworkImportFailed")
        eventSink?.success(mapOf(
                "eventName" to "onNetworkImportFailed",
                "error" to error
        ))
    }

    override fun onNetworkLoaded(meshNetwork: MeshNetwork?) {
        Log.d(this.javaClass.name, "onNetworkLoaded")
        if (meshNetwork == null) {
            return
        }
        if (doozMeshNetwork == null || doozMeshNetwork?.meshNetwork?.id != meshNetwork.id) {
            doozMeshNetwork = DoozMeshNetwork(binaryMessenger, meshNetwork)
        } else {
            doozMeshNetwork?.meshNetwork = meshNetwork
        }
        eventSink?.success(mapOf(
                "eventName" to "onNetworkLoaded",
                "id" to meshNetwork.id,
                "meshName" to meshNetwork.meshName,
                "isLastSelected" to meshNetwork.isLastSelected
        ))
    }

    override fun onNetworkUpdated(meshNetwork: MeshNetwork?) {
        Log.d(this.javaClass.name, "onNetworkUpdated")
        if (meshNetwork == null) {
            return
        }
        doozMeshNetwork?.meshNetwork = meshNetwork
        eventSink?.success(mapOf(
                "eventName" to "onNetworkUpdated",
                "id" to meshNetwork.id,
                "meshName" to meshNetwork.meshName,
                "isLastSelected" to meshNetwork.isLastSelected
        ))
    }

    override fun sendProvisioningPdu(meshNode: UnprovisionedMeshNode?, pdu: ByteArray?) {
        Log.d(this.javaClass.name, "sendProvisioningPdu")
    }
}