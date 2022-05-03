import Box from '@mui/material/Box';
import MDTable from 'Components/UI/Atoms/MDTable/MDTable';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import CreateIcon from '@mui/icons-material/Create';
import { Link } from 'react-router-dom';
import { useSettingStore } from 'Components/Store/SettingStore/SettingStore';
import BuildData from 'Components/MDClass/BuildData/BuildData';
import GitData from 'Components/MDClass/GitData/GitData';
import NginxData from 'Components/MDClass/NginxData/NginxData';
import DBData from 'Components/MDClass/DBData/DBData';

export default function Home() {
  const cleanProjectId = useSettingStore((state) => state.setProjectId);
  const cleanProjecttName = useSettingStore((state) => state.setProjectName);
  const cleanBuildConfigs = useSettingStore((state) => state.setBuildConfigs);
  const cleanDBConfigs = useSettingStore((state) => state.setDBConfigs);
  const cleanGitConfig = useSettingStore((state) => state.setGitConfig);
  const cleanNginxConfig = useSettingStore((state) => state.setNginxConfig);

  const handleCreateClick = () => {
    cleanProjectId(0);
    cleanProjecttName('');
    cleanBuildConfigs([new BuildData()]);
    cleanDBConfigs([new DBData()]);
    cleanGitConfig(new GitData());
    cleanNginxConfig(new NginxData());
  };
  return (
    <Box sx={{ marginTop: 5 }}>
      <MDTable />
      <Stack mt={2} direction="row" justifyContent="flex-end" spacing={2}>
        <Link
          to="/setting/0"
          style={{ color: 'white', textDecoration: 'none' }}
        >
          <Button
            sx={{
              background: 'linear-gradient(195deg, #666, #191919)',
            }}
            variant="contained"
            endIcon={<CreateIcon />}
            onClick={handleCreateClick}
          >
            Create
          </Button>
        </Link>
      </Stack>
    </Box>
  );
}
