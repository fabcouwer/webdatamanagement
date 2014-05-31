public static TPEStack personStack() {
		PatternNode person = new PatternNode("person");
		PatternNode email = new PatternNode("email");
		PatternNode name = new PatternNode("name");
		PatternNode last = new PatternNode("last");

		TPEStack personStack = new TPEStack(person, null);
		TPEStack nameStack = new TPEStack(name, personStack);
		TPEStack lastStack = new TPEStack(last, personStack);
		TPEStack emailStack = new TPEStack(email, personStack);

		personStack.addChildStack(nameStack);
		personStack.addChildStack(emailStack);
		personStack.addChildStack(nameStack);
		nameStack.addChildStack(lastStack);
		
		person.addChild(name);
		person.addChild(email);
		name.addChild(last);
		
		return personStack;
	}