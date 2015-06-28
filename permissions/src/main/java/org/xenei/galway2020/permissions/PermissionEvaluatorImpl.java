package org.xenei.galway2020.permissions;

import java.util.Set;

import org.apache.jena.permissions.SecurityEvaluator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class PermissionEvaluatorImpl implements SecurityEvaluator {

	public boolean evaluate(Object principal, Action action, SecNode graphIRI) {
		if (action == Action.Read)
		{
			// anybody can read
			return true;
		}
		//authenticated users can do anything.
		return  ((Subject)principal).isAuthenticated();
			
	}

	public boolean evaluate(Object principal, Set<Action> actions, SecNode graphIRI) {
		if (actions.contains( Action.Create) || actions.contains( Action.Delete) || actions.contains( Action.Update))
		{
			//authenticated users can do anything.
			return  ((Subject)principal).isAuthenticated();
		}
		// only action left is Read and everyone can do that.
		return true;
	}

	public boolean evaluate(Object principal, Action action, SecNode graphIRI,
			SecTriple arg3) {
		return evaluate(principal, action, graphIRI );
	}

	public boolean evaluate(Object principal, Set<Action> actions, SecNode graphIRI,
			SecTriple arg3) {
		return evaluate(principal, actions, graphIRI );
	}

	public boolean evaluateAny(Object principal, Set<Action> actions, SecNode graphIRI) {
		return actions.contains( Action.Read) || ((Subject)principal).isAuthenticated();
			
	}

	public boolean evaluateAny(Object principal, Set<Action> actions, SecNode graphIRI,
			SecTriple arg3) {
		return evaluateAny( principal, actions, graphIRI );
	}

	public boolean evaluateUpdate(Object principal, SecNode graphIRI, SecTriple from,
			SecTriple to) {
		return evaluate( principal, Action.Update, graphIRI );
	}

	public Subject getPrincipal() {
		return SecurityUtils.getSubject();
	}

}
