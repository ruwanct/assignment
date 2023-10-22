import React from 'react';
import {Box, Heading} from 'grommet';

const Header = ({ align = 'center', label, level, size}) => {
  const textAlign = align === 'center' ? align : undefined;
  return (
    <Box align={align}>
      <Heading level={level} size={size} textAlign={textAlign} margin="none">
        {label}
      </Heading>
    </Box>
  );
};
export default Header;
