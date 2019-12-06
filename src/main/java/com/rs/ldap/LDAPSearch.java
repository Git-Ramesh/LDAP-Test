package com.rs.ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * @author ramesh
 */
public class LDAPSearch {
    public static void main(String[] args) {
        DirContext dirContext = null;
        Hashtable<String,String> env = new Hashtable<String, String>(10, 1.0f);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        try{
            dirContext = new InitialDirContext(env);
            System.out.println("Connected");
            String filter = "(objectClass=organizationalPerson)";
            String[] requiredAttributes = {"cn","sn","description", "ou", "userPassword"};
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            controls.setReturningAttributes(requiredAttributes);
            NamingEnumeration<SearchResult> users = dirContext.search("dc=radiant,dc=com", filter, controls);
             while(users.hasMore()) {
                 SearchResult searchResult = (SearchResult)users.next();
                 Attributes attributes = searchResult.getAttributes();
                 System.out.println("cn? "+ attributes.get("cn").get(0).toString());
                 System.out.println("sn? "+ attributes.get("sn").get(0).toString());
                 System.out.println("description? " + attributes.get("description").get(0).toString());
                 System.out.println("ou? " +  attributes.get("ou").get(0).toString());
                 System.out.println("userPassword? " + attributes.get("userPassword").get(0).toString());
                 System.out.println("---------------------------------------------");
             }   
            
        } 
        catch(NamingException ne) {
            
        } finally {
            if(dirContext != null) {
               try {
                   dirContext.close();
               } catch(NamingException ex) {
                   ex.printStackTrace();
               }
            }
        }
    }
}
