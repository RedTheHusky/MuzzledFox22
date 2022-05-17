package nsfw.chastity.voting;

public class ChastityVotingTeachable {

    private ChastityVotingTeachable()
    {

    }
    /*public String Name;
    public User TeacherID;
    public TextChannel[] CreatedChannels;
    public Message React2Enroll;
    public Category MainCategory;
    public Role ClassRole;
    public List<ChastityVotingStudent> Students;
    public ChastityVotingTeachable(User Teacher, String Name, Guild g)
    {
        this.Name = Name;
        TeacherID = Teacher;
        Students = new List<ChastityVotingStudent>();

        CreatedChannels = new TextChannel[3];



        ClassRole = g.createRole().complete();
        ClassRole.getManager().setName(Name+" Class").complete();
        MainCategory = g.createCategory(Name).complete();
        CreatedChannels[0] = MainCategory.createTextChannel(Name+"-Tasks").complete();
        CreatedChannels[1] = MainCategory.createTextChannel(Name+"-Verify").complete();
        CreatedChannels[2] = MainCategory.createTextChannel(Name+"-Feedback").complete();
        PermissionOverride perm0=MainCategory.createPermissionOverride(g.getPublicRole()).complete();
        PermissionOverride perm1=MainCategory.createPermissionOverride(g.getMember(TeacherID)).complete();
        PermissionOverride perm2=MainCategory.createPermissionOverride(ClassRole).complete();
        perm0.getManager().setDeny(Permission.MESSAGE_READ,Permission.MESSAGE_WRITE).complete();
        perm1.getManager().setAllow(Permission.MESSAGE_READ).complete();
        perm2.getManager().setAllow(Permission.MESSAGE_READ,Permission.MESSAGE_WRITE).complete();
        PermissionOverride perm4=CreatedChannels[1].createPermissionOverride(g.getPublicRole()).complete();
        PermissionOverride perm5=CreatedChannels[1].createPermissionOverride(g.getMember(TeacherID)).complete();
        PermissionOverride perm6=CreatedChannels[1].createPermissionOverride(ClassRole).complete();
        perm4.getManager().setDeny(Permission.MESSAGE_READ,Permission.MESSAGE_WRITE).complete();
        perm5.getManager().setDeny(Permission.MESSAGE_READ,Permission.MESSAGE_WRITE).complete();
        perm6.getManager().setAllow(Permission.MESSAGE_READ,Permission.MESSAGE_WRITE).complete();
    }

    public override string ToString()
    {
        String s = "";
        s += Name + ";";
        s += TeacherID.getId() + ";";
        s += CreatedChannels.length + ";";
        foreach (TextChannel tc in CreatedChannels)
        s += tc.Id.ToString() + ";";

        s += MainCategory.Id.ToString() + ";";
        s += ClassRole.Id.ToString() + ";";
        s += React2Enroll.Id.ToString() + ";";
        s += Students.Count().ToString() + ";";
        foreach (Student st in Students)
        {
            s += st.ToString();
        }
        return s;
    }

    public bool IsValid()
    {
        int s = 0;
        try
        {
            s += (int)TeacherID.Id;
            foreach (ITextChannel tc in CreatedChannels)
            s += (int)tc.Id;
            s += (int)React2Enroll.Id;
            s += (int)MainCategory.Id;
            s += (int)React2Enroll.Id;
            s += (int)ClassRole.Id;
            s += Students.Count; //Compiler doesnt remove this
            //Basically if it crashes we return false, if it doesnt we return true since im lazy
            return true;
        }
        catch(Exception e)
        {

        }
        return false;
    }

    public static Teachable FromString(IGuild g, string input, out string output)
    {
        Teachable t = new Teachable();
        List<string> str = input.Split(';').ToList();
        string Name = str.First();
        input = input.Substring(str.First().Length+1);
        str.RemoveAt(0);

        try
        {
            t.TeacherID = g.GetUserAsync(ulong.Parse(str.First())).GetAwaiter().GetResult();
        }
        catch (Exception e) {

        }
        input = input.Substring(str.First().Length+1);
        str.RemoveAt(0);

        int l = int.Parse(str.First());
        input = input.Substring(str.First().Length+1);
        str.RemoveAt(0);

        t.CreatedChannels = new ITextChannel[l];
        for (int i = 0; i < l; i++)
        {
            try
            {
                t.CreatedChannels[i] = (ITextChannel)g.GetChannelAsync(ulong.Parse(str.First())).GetAwaiter().GetResult();
            }
            catch(Exception e) {

            }
            input = input.Substring(str.First().Length+1);
            str.RemoveAt(0);
        }

        try
        {
            t.MainCategory = g.GetCategoriesAsync().GetAwaiter().GetResult().Where(x => x.Id == ulong.Parse(str.First())).ElementAt(0);
        }
        catch (Exception e)
        {

        }
        input = input.Substring(str.First().Length + 1);
        str.RemoveAt(0);

        try
        {
            t.React2Enroll = (IUserMessage)((ITextChannel)g.GetChannelsAsync().GetAwaiter().GetResult().Where(x => x.Name.Contains("Enroll")).First()).GetMessageAsync(ulong.Parse(str.First())).GetAwaiter().GetResult();
        }
        catch (Exception e)
        {

        }
        input = input.Substring(str.First().Length + 1);
        str.RemoveAt(0);

        try
        {
            t.ClassRole = g.GetRole(ulong.Parse(str.First()));
        }
        catch (Exception e)
        {

        }
        input = input.Substring(str.First().Length + 1);
        str.RemoveAt(0);

        l = int.Parse(str.First());
        input = input.Substring(str.First().Length + 1);
        str.RemoveAt(0);

        t.Students = new List<Student>();
        for (int i = 0; i < l; i++)
        {
            string outputt = input;
            t.Students.Add(Student.FromString(g,input,out outputt));
            if (t.Students.ElementAt(i).Member == null)
                t.Students.RemoveAt(i);
            input = outputt;
        }


        output = input;
        return t;
    }*/
}
