var asmapi = Java.type('net.minecraftforge.coremod.api.ASMAPI')
var opc = Java.type('org.objectweb.asm.Opcodes')
var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode')
var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode')
var LdcInsnNode = Java.type('org.objectweb.asm.tree.LdcInsnNode')
var TypeInsnNode = Java.type('org.objectweb.asm.tree.TypeInsnNode')
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode')

function initializeCoreMod() {
    return {
    	'HumanALayer': {
    		'target': {
    			'type': 'CLASS',
    			'name': 'net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer'
    		},
    		'transformer': function(classNode) {
    			var count = 0
    			var fn = "renderArmorPiece"
    			for (var i = 0; i < classNode.methods.size(); ++i) {
    				var obj = classNode.methods.get(i)
    				if (obj.name == fn) {
    					patch_renderAP(obj)
    					count++
    				}
    			}
    			if (count < 1)
    				asmapi.log("ERROR", "Failed to modify HumanoidArmorLayer: Method not found")
    			return classNode;
    		}
    	}
    }
}

// add default color value
function patch_renderAP(obj) {
	var n1 = "getOrDefault"
	var o1 = "net/minecraft/world/item/component/DyedItemColor"
	var d1 = "(Lnet/minecraft/world/item/ItemStack;I)I"
	var node = asmapi.findFirstMethodCall(obj, asmapi.MethodType.STATIC, o1, n1, d1)
	if (node) {
		var node2 = node.getPrevious().getPrevious()
		var node3 = node.getNext().getNext()
		if (node2.getOpcode() == opc.ALOAD && node2.getNext().getOpcode() == opc.LDC && node3.getOpcode() == opc.GOTO) {
			var o2 = "net/minecraft/world/item/ItemStack"
			var n2 = "getItem"
			var d2 = "()Lnet/minecraft/world/item/Item;"
			var op2 = asmapi.buildMethodCall(o2, n2, d2, asmapi.MethodType.VIRTUAL)
			var lbl = new LabelNode()
			var op1 = new VarInsnNode(opc.ALOAD, node2.var) // itemstack
			var op3 = new TypeInsnNode(opc.INSTANCEOF, "com/lupicus/ea/item/EAArmorItem")
			var op4 = new JumpInsnNode(opc.IFNE, lbl)
			var list = asmapi.listOf(op1, op2, op3, op4)
			obj.instructions.insertBefore(node2, list)
			var op1 = op1.clone(null) // aload
			var op2 = new LdcInsnNode(-3487544)
			var op3 = node.clone(null) // call
			var op4 = node.getNext().clone(null) // call
			var op5 = new JumpInsnNode(opc.GOTO, node3.label)
			var list = asmapi.listOf(lbl, op1, op2, op3, op4, op5)
			obj.instructions.insert(node3, list)
		}
		else
			asmapi.log("ERROR", "Failed to modify renderArmorPiece: call is different")
	}
	else
		asmapi.log("ERROR", "Failed to modify renderArmorPiece: call not found")
}
