public class Airport implements InvariantCheck {
    // State
    private VDMSet permissions;
    private VDMSet landed;

    // Initialize the state
    public Airport() {
        this.permissions = new VDMSet();
        this.landed = new VDMSet();
        VDM.invTest(this);
    }

    // State invariant
    @Override
    public boolean inv() {
        return landed.isASubsetOf(permissions);
    }

    // Operation: givePermission
    public void givePermission(Aircraft craftIn) {
        VDM.preTest(this.permissions.doesNotContain(craftIn));
        permissions = permissions.union(new VDMSet(craftIn));
        VDM.invTest(this);
    }

    // Operation: recordLanding
    public void recordLanding(Aircraft craftIn) {
        VDM.preTest(permissions.contains(craftIn) && landed.doesNotContain(craftIn));
        if (permissions.contains(craftIn) && landed.doesNotContain(craftIn)) {
            landed = landed.union(new VDMSet(craftIn));
            VDM.invTest(this);
        }
    }

    // Operation: recordTakeOff
    public void recordTakeOff(Aircraft craftIn) {
        VDM.preTest(landed.contains(craftIn));
        if (landed.contains(craftIn)) {
            landed = landed.difference(new VDMSet(craftIn));
            permissions = permissions.difference(new VDMSet(craftIn));
            VDM.invTest(this);
        }
    }

    // Operation: getPermission
    public VDMSet getPermission() {
        return permissions;
    }

    // Operation: getLanded
    public VDMSet getLanded() {
        return landed;
    }

    // Operation: numberWaiting
    public int numberWaiting() {
        return permissions.difference(landed).card();
    }
}

