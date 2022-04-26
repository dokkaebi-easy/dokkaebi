import React from 'react';
import { useStore } from 'Components/Store/settingStore';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import axios from 'axios';

interface PropertyAxios {
  first: string;
  second: string;
}

interface buildConfigAxios {
  frameworkName: string;
  name: string;
  version: string;
  type: string;
  projectDirectory: string;
  buildPath: string;
  env: PropertyAxios[];
  publish: PropertyAxios[];
  volume: PropertyAxios[];
}

export default function AxiosPage() {
  const projectName = useStore((state) => state.projectName);
  const buildConfigs = useStore((state) => state.buildConfigs);
  const gitConfig = useStore((state) => state.gitConfig);
  const nginxConfig = useStore((state) => state.nginxConfig);

  const handleClick = () => {
    const params = {
      buildConfigs,
      gitConfig,
      nginxConfig,
      projectName,
    };
    console.log(JSON.stringify(params));

    // axios.post('/api/project', { params }).then((res) => {
    //   console.log(res.data);
    // });
    console.log(params);
    console.log(projectName);
    console.log(buildConfigs);
    console.log(gitConfig);
    console.log(nginxConfig);
  };
  return (
    <Box>
      <Box>Run Build</Box>
      <Button variant="outlined" size="small" onClick={handleClick}>
        Submit
      </Button>
    </Box>
  );
}
