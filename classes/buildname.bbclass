#
# Query the system for CRM/AU identity information
#
# Use crm_id if defined, otherwise
# Run 'git describe' for the latest tag
#
def get_git_latest_tag(path, d):
    if os.path.exists(path) :
        return oe_run(d, ["git", "describe"], cwd=path).rstrip()
    else :
        return time.strftime('%Y%m%d%H%M')
