Part I:

1. Implement an evaluation algorithm for C-TP tree-patterns. At the end of the execution,
the stacks should contain only those Match objects that participate to complete query
answers.

2. Implement an algorithm that computes the result tuples of C-TP tree patterns, out of
the stacks’ content.


Part II:

1. Extend the evaluation algorithm developed in (1.) at the end of the previous section to“*” wildcards. 
For this, Stack objects are allowed to be created for *-labeled query tree nodes. Also the startElement and endElement handlers are adapted.

2. Extend the evaluation algorithm to optional nodes, by modifying the tests performed
in endElement (looking for children which the Match should have) to avoid pruning a
Match if only optional children are missing.

3. Extend the algorithm developed in (2.) to handle optional nodes, by filling in partial
result tuples with nulls as necessary.

4. Extend the evaluation algorithm to support value predicates, by (i) implementing a
handler for the characters(...) SAX method, in order to record the character data
contained within an XML element and (ii) using it to compare the text values of XML
elements for which Match objects are created, to the value predicates imposed in the
query.

5. Extend the algorithm in (2.) to return subtrees and not only preorder numbers. The
subtrees are represented using the standard XML syntax with opening/closing tags.