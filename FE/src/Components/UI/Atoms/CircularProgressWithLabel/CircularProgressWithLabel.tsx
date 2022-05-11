import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Skeleton from '@mui/material/Skeleton';
import { keyframes } from '@emotion/react';
import { useEffect, useState } from 'react';
import styles from './CircularProgressWithLabel.module.css';

const styleBox = {
  top: 0,
  left: 0,
  bottom: 0,
  right: 0,
  margin: 'auto auto',
  position: 'absolute',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  backgroundColor: 'white',
  borderRadius: 100,
};

const animate = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
`;

const buildsuccess =
  'linear-gradient(45deg, rgb(150, 235, 150), rgb(0, 220, 0))';
const buildfail = 'linear-gradient(45deg, rgb(235, 150, 150), rgb(220, 0, 0))';
const buildwait = 'rgb(245, 245,245)';

interface ProgressProps {
  value: string;
}

export default function CircularProgressWithLabel({ value }: ProgressProps) {
  const [color, setColor] = useState(buildwait);

  useEffect(() => {
    if (value === 'Done') {
      setColor(buildsuccess);
    } else if (value === 'Failed') {
      setColor(buildfail);
    } else {
      setColor(buildwait);
    }
  }, [value]);
  return (
    <Box
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
    >
      <Box
        sx={{
          ...styleBox,
          position: 'relative',
          display: 'inline-flex',
          background: `${color}`,
          borderRadius: 100,
        }}
        width={300}
        height={300}
        overflow="hidden"
      >
        {value === 'Processing' ? (
          <Box
            className={styles.Loader}
            sx={{
              position: 'absolute',
              top: '50%',
              left: '50%',
              background: 'transparent ',
              transformOrigin: 'top left',
              animation: `${animate} 1.5s linear infinite`,
            }}
            width="100%"
            height="100%"
          />
        ) : (
          <Box />
        )}
        <Box
          sx={{
            ...styleBox,
            backgroundColor: 'rgb(240, 240,240)',
            // border: '3px dashed rgb(230, 230,230)',
          }}
          width="70%"
          height="70%"
        >
          <Typography color="text.secondary" variant="h5">
            {value}
          </Typography>
        </Box>
      </Box>
    </Box>
  );
}
