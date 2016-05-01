package net.runelite.asm.attributes.code.instructions;

import net.runelite.asm.attributes.code.Instruction;
import net.runelite.asm.attributes.code.InstructionType;
import net.runelite.asm.attributes.code.Instructions;
import net.runelite.asm.execution.Frame;
import net.runelite.asm.execution.InstructionContext;
import net.runelite.asm.execution.Stack;
import net.runelite.asm.execution.StackContext;
import net.runelite.asm.execution.Value;

public class IAdd extends Instruction
{
	public IAdd(Instructions instructions, InstructionType type, int pc)
	{
		super(instructions, type, pc);
	}
	
	public IAdd(Instructions instructions)
	{
		super(instructions, InstructionType.IADD, -1);
	}

	@Override
	public InstructionContext execute(Frame frame)
	{
		InstructionContext ins = new InstructionContext(this, frame);
		Stack stack = frame.getStack();
		
		StackContext two = stack.pop();
		StackContext one = stack.pop();
		
		ins.pop(two, one);
		
		Value result = Value.UNKNOWN;
		if (!two.getValue().isUnknownOrNull() && !one.getValue().isUnknownOrNull())
		{
			int i2 = (int) two.getValue().getValue(),
				i1 = (int) one.getValue().getValue();
			
			result = new Value(i1 + i2);
		}
		
		StackContext ctx = new StackContext(ins, int.class, result);
		stack.push(ctx);
		
		ins.push(ctx);
		
		return ins;
	}
}
