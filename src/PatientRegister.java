public class PatientRegister implements InvariantCheck{
    public static final int LIMIT = 200;
    private VDMSet reg;

    @Override
    public boolean inv() {
        return reg.card() <= LIMIT;
    }
    public PatientRegister() {
        this.reg = new VDMSet();
        VDM.invTest(this);
    }
    public void addPatient(Patient patientIn) {
        VDM.preTest(reg.doesNotContain(patientIn));
        reg = reg.union(new VDMSet(patientIn));
        VDM.invTest(this);
    }
    public void removePatient(Patient patientIn) {
        VDM.preTest(reg.contains(patientIn));
        reg = reg.difference(new VDMSet(patientIn));
        VDM.invTest(this);
    }
    public VDMSet getPatients() {
        return this.reg;
    }
    public boolean isRegistered(Patient patientIn) {
        return reg.contains(patientIn);
    }
    public int numberRegistered() {
        return reg.card();
    }





}
