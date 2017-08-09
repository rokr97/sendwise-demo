package org.example.sendwise.demo;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.cayenne.CayenneModule;
import io.bootique.jersey.JerseyModule;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.example.sendwise.demo.persistent.Account;
import org.example.sendwise.demo.ws.AccountResource;

import java.math.BigDecimal;

public class App implements Module {
    @Inject
    private Provider<ServerRuntime> runtimeProvider;

    public static void main(String[] args) throws Exception {
        BQRuntime r = Bootique.app(args).module(App.class).autoLoadModules().createRuntime();
        ServerRuntime cayenneRuntime = r.getInstance(ServerRuntime.class);
        createUserAccounts(cayenneRuntime);
        r.run();
    }

    private static void createUserAccounts(ServerRuntime cayenneRuntime) {
        ObjectContext context = cayenneRuntime.newContext();

        Account alice = context.newObject(Account.class);
        alice.setAccno("100500666");
        alice.setBalance(BigDecimal.valueOf(100.50));
        alice.setCurrency("USD");
        alice.setName("Alice");

        Account bob = context.newObject(Account.class);
        bob.setAccno("1337");
        bob.setBalance(BigDecimal.valueOf(13.37));
        bob.setCurrency("EUR");
        bob.setName("Bob");

        context.commitChanges();
    }

    @Override
    public void configure(Binder binder) {
        // add all classes in AccountResource's class package as REST API resources
        JerseyModule.extend(binder).addPackage(AccountResource.class.getPackage());

        // non-default Cayenne project name requires an explicit declaration
        CayenneModule.extend(binder)
                .addProject("cayenne-sendwise.xml");
    }

    static class SchedulerStarter {
        @Inject
        public void initTrigger(ServerRuntime cayenneRuntime) {
            System.out.println("runtime: " + cayenneRuntime);
        }
    }
}
